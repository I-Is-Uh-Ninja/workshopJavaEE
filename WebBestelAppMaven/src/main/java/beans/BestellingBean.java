/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Artikel;
import entity.Bestelling;
import entity.BestellingHasArtikel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.BestellingFacade;
import session.BestellingHasArtikelFacade;
import session.KlantFacade;

/**
 *
 * @author Ian
 */
@Named
@Dependent
@Stateless
public class BestellingBean {

    private List<Bestelling> bestellingen;
    private Bestelling selectedBestelling;
    private Integer klantId;
    //private List<Klant> klanten;
    private List<BestellingHasArtikel> artikelenInBestelling;
    private BigDecimal totaalPrijs;
    
    public BestellingBean(){
        totaalPrijs = new BigDecimal(0);
        totaalPrijs = totaalPrijs.setScale(2, RoundingMode.HALF_UP);
    }
    
    @EJB
    private BestellingFacade bestellingFacade;
    
    @EJB
    private BestellingHasArtikelFacade bHAFacade;
    
    @EJB
    private KlantFacade klantFacade;
    
    //=====Getters and Setters=========
    
    public List<Bestelling> getBestellingen(){
        return bestellingen;
    }
    
    public void setBestellingen(List<Bestelling> bestellingen){
        this.bestellingen = bestellingen;
    }
    
    public Bestelling getSelectedBestelling(){
        return selectedBestelling;
    }
    
    public void setSelectedBestelling(Bestelling bestelling){
        selectedBestelling = bestelling;
    }
    
    public Integer getKlantId(){
        return klantId;
    }
    
    public void setKlantId(Integer id){
        klantId = id;
    }

   /* public List<Klant> getKlanten() {
        return klanten;
    }

    public void setKlanten(List<Klant> klanten) {
        this.klanten = klanten;
    }*/

    public List<BestellingHasArtikel> getArtikelenInBestelling() {
        return artikelenInBestelling;
    }

    public void setArtikelenInBestelling(List<BestellingHasArtikel> artikelenInBestelling) {
        this.artikelenInBestelling = artikelenInBestelling;
    }

    public BigDecimal getTotaalPrijs() {
        calculateTotaalPrijs();
        return totaalPrijs;
    }

    public void setTotaalPrijs(BigDecimal totaalPrijs) {
        this.totaalPrijs = totaalPrijs;
    }
        
    //=====Adding and removing from bestelling list=====
    
    public void addToBestellingen(Bestelling bestelling){
        bestellingFacade.create(bestelling);
        setBestellingen(bestellingFacade.findAll());
    }
    
    public void removeFromBestellingen(Bestelling bestelling){
        int bestellingenSize = bestellingen.size();
        int bestellingIndex = -1;
        for(int i=0; i<bestellingenSize; i++){
            if(bestellingen.get(i).getIdBestelling() == bestelling.getIdBestelling()){
                bestellingFacade.remove(bestelling);
            }
        }
        setBestellingen(bestellingFacade.findAll());
    }
    
    public void addSelectedBestelling(){
        Bestelling bestelling = new Bestelling();
        bestelling.setKlantidKlant(klantFacade.find(klantId));
        addToBestellingen(bestelling);
    }
    
    private void addToArtikelenInBestelling(BestellingHasArtikel bHA){
        bHAFacade.create(bHA);
        setArtikelenInBestelling(bHAFacade.findArtikelenInBestelling(selectedBestelling.getIdBestelling()));
    }
    
    private void removeFromArtikelenInBestelling(BestellingHasArtikel bHA){
        bHAFacade.remove(bHA);
        setArtikelenInBestelling(bHAFacade.findArtikelenInBestelling(selectedBestelling.getIdBestelling()));
    }
    
    //=====Go to other views=====
    
    public String goToViewBestelling(Bestelling bestelling){
        setSelectedBestelling(bestelling);
        setArtikelenInBestelling((List<BestellingHasArtikel>) bestelling.getBestellingHasArtikelCollection());
        return "viewBestelling";
    }
    
    //=====Edit bestelling=====
    
    public void removeArtikelFromBestelling(BestellingHasArtikel selectedArtikel){
        removeFromArtikelenInBestelling(selectedArtikel);
        calculateTotaalPrijs();
    }
    
    public void editAantal(BestellingHasArtikel selectedArtikel){
        bHAFacade.edit(selectedArtikel);
        setArtikelenInBestelling(bHAFacade.findArtikelenInBestelling(selectedBestelling.getIdBestelling()));
        calculateTotaalPrijs();
    }
    
    public String addArtikelToBestelling(Artikel artikel){
        BestellingHasArtikel bestHasArt = new BestellingHasArtikel();
        bestHasArt.setAantal(1);
        bestHasArt.setArtikelidArtikel(artikel);
        bestHasArt.setBestellingidBestelling(selectedBestelling);
        addToArtikelenInBestelling(bestHasArt);
        setSelectedBestelling(bestellingFacade.find(selectedBestelling.getIdBestelling()));
        return "viewBestelling";
    }
    
    //=====Other=====
    
    private void calculateTotaalPrijs(){
        List<BestellingHasArtikel> artikelen = getArtikelenInBestelling();
        double prijs = 0;
        for(BestellingHasArtikel bha : artikelen){
            prijs += bha.getArtikelidArtikel().getArtikelprijs().doubleValue() * bha.getAantal();
        }
        BigDecimal totaal = new BigDecimal(prijs);
        totaal = totaal.setScale(2, RoundingMode.HALF_UP);
        setTotaalPrijs(totaal);
    }
    
    public void findBestellingenByKlantId(int klantId){
        setKlantId(klantId);
        setBestellingen(bestellingFacade.findBestellingByKlantId(klantId));
    }
    
    
    @PostConstruct
    public void init(){
        if (selectedBestelling != null){
            if(selectedBestelling.getIdBestelling() != null){
                artikelenInBestelling = bHAFacade.findArtikelenInBestelling(selectedBestelling.getIdBestelling());
            }
        }
    }
    
}
