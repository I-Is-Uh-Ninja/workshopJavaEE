/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Artikel;
import entity.Bestelling;
import entity.BestellingHasArtikel;
import entity.Klant;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedProperty;
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
    private List<Klant> klanten;
    private int aantal;
    
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

    public List<Klant> getKlanten() {
        return klanten;
    }

    public void setKlanten(List<Klant> klanten) {
        this.klanten = klanten;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
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
    
    //=====Go to other views=====
    
    public String goToViewBestelling(Bestelling bestelling){
        setSelectedBestelling(bestelling);
        return "viewBestelling";
    }
    
    //=====Edit bestelling=====
    
    public void removeArtikelFromBestelling(BestellingHasArtikel selectedArtikel){
        bHAFacade.remove(selectedArtikel);
    }
    
    public void editAantal(BestellingHasArtikel selectedArtikel){
        bHAFacade.edit(selectedArtikel);
    }
    
    public String addArtikelToBestelling(Artikel artikel){
        BestellingHasArtikel nieuwArtikel = new BestellingHasArtikel();
        nieuwArtikel.setAantal(aantal);
        nieuwArtikel.setArtikelidArtikel(artikel);
        nieuwArtikel.setBestellingidBestelling(selectedBestelling);
        bHAFacade.create(nieuwArtikel);
        selectedBestelling = bestellingFacade.find(selectedBestelling.getIdBestelling());
        return "viewBestelling";
    }
    
    //=====Other=====
    
    @PostConstruct
    public void init(){
        bestellingen = bestellingFacade.findAll();
        klanten = klantFacade.findAll();
    }
    
}
