/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Bestelling;
import entity.Betaling;
import entity.Factuur;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.BetalingFacade;
import session.FactuurFacade;

/**
 *
 * @author Ian
 */
@Named
@Dependent
@Stateless
public class FactuurBean {
    
    private Bestelling selectedBestelling;
    private List<Betaling> betalingen;
    private Factuur factuur;
    private List<Factuur> facturenInBestelling;
    private Map<Betaling, Factuur> betalingenInFactuur;
    
    @EJB
    BetalingFacade betalingFacade;
    
    @EJB
    FactuurFacade factuurFacade;
    
    //=====Constructor=====
    
    public FactuurBean() {
        factuur = new Factuur();
        betalingenInFactuur = new HashMap();
    }
    
    //=====Getters and setters=====
    
    public Bestelling getSelectedBestelling() {
        return selectedBestelling;
    }

    public void setSelectedBestelling(Bestelling selectedBestelling) {
        this.selectedBestelling = selectedBestelling;
    }

    public List<Betaling> getBetalingen() {
        return betalingen;
    }

    public void setBetalingen(List<Betaling> betalingen) {
        this.betalingen = betalingen;
    }

    public Factuur getFactuur() {
        return factuur;
    }

    public void setFactuur(Factuur factuur) {
        this.factuur = factuur;
    }

    public List<Factuur> getFacturenInBestelling() {
        return facturenInBestelling;
    }

    public void setFacturenInBestelling(List<Factuur> facturenInBestelling) {
        this.facturenInBestelling = facturenInBestelling;
    }

    public Map<Betaling, Factuur> getBetalingenInFactuur() {
        return betalingenInFactuur;
    }

    public void setBetalingenInFactuur(Map<Betaling, Factuur> betalingenInFactuur) {
        this.betalingenInFactuur = betalingenInFactuur;
    }
    
    
    
    //=====Add to/remove from betalingen=====
    
    public void addToBetalingen(Betaling betaling){
        if(betalingen == null){
            setBetalingen(new ArrayList<Betaling>());
        }
        betalingen.add(betaling);
    }
    
    public void removeFromBetalingen(Betaling betaling){
        if(betalingen != null){
            betalingen.remove(betaling);
        }
    }
    
    public List<Betaling> findBetalingenByFactuur(Factuur factuur){
        int size = betalingenInFactuur.size();
        List<Betaling> returnBetalingen = new ArrayList<>();
        for (Map.Entry<Betaling, Factuur> next : betalingenInFactuur.entrySet()) {
            if(next.getValue().equals(factuur)){
                returnBetalingen.add(next.getKey());
            }
        }
        return returnBetalingen;
    }
    
    //=====Add factuur=====
    
    public String payBestelling(Betaling betaling){
        factuur.setBestellingidBestelling(selectedBestelling);
        factuur.setFactuurDatum(new Date());
        betaling.setFactuuridFactuur(factuur);
        betaling.setKlantidKlant(selectedBestelling.getKlantidKlant());
        setBetalingen((List<Betaling>)factuur.getBetalingCollection());
        addToBetalingen(betaling);
        factuur.setBetalingCollection(betalingen);
        factuurFacade.create(factuur);
        setFacturenInBestelling(factuurFacade.findFactuurByBestellingId(selectedBestelling.getIdBestelling()));
        return "viewBestelling";
    }
    
    public List<Factuur> findFacturen(Bestelling bestelling){
        setSelectedBestelling(bestelling);
        setFacturenInBestelling(factuurFacade.findFactuurByBestellingId(bestelling.getIdBestelling()));
        for(Factuur f : facturenInBestelling){
            for(Betaling b : betalingFacade.findByFactuur(f.getIdFactuur())){
                betalingenInFactuur.put(b, f);
            }
        }
        return facturenInBestelling;
    }
}
