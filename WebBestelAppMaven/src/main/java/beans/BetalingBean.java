/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Betaalwijze;
import entity.Betaling;
import entity.Factuur;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.BetaalwijzeFacade;
import session.BetalingFacade;

/**
 *
 * @author Ian
 */
@Named
@Dependent
@Stateless
public class BetalingBean {
    
    private Betaalwijze selectedBetaalwijze;
    private List<Betaalwijze> allBetaalwijzes;
    private Betaling betaling;
    private Integer betaalwijzeId;
    
    @EJB
    BetalingFacade betalingFacade;
    
    @EJB
    BetaalwijzeFacade betaalwijzeFacade;

    //Constructor
    public BetalingBean() {
        betaling = new Betaling();
    }
    //=====Getters and setters=====
    
    public Betaalwijze getSelectedBetaalwijze() {
        return selectedBetaalwijze;
    }

    public void setSelectedBetaalwijze(Betaalwijze selectedBetaalwijze) {
        this.selectedBetaalwijze = selectedBetaalwijze;
    }

    public List<Betaalwijze> getAllBetaalwijzes() {
        return allBetaalwijzes;
    }

    private void setAllBetaalwijzes(List<Betaalwijze> allBetaalwijzes) {
        this.allBetaalwijzes = allBetaalwijzes;
    }

    public Betaling getBetaling() {
        return betaling;
    }

    public void setBetaling(Betaling betaling) {
        this.betaling = betaling;
    }

    public Integer getBetaalwijzeId() {
        return betaalwijzeId;
    }

    public void setBetaalwijzeId(Integer betaalwijzeId) {
        this.betaalwijzeId = betaalwijzeId;
    }
    
    //=====Adding betaling=====
    
    public Betaling addBetaling(){
        try { 
            betaling.setBetaalDatum(new Date());
            setSelectedBetaalwijze(betaalwijzeFacade.find(betaalwijzeId));
        betaling.setBetaalwijzeidBetaalwijze(selectedBetaalwijze);
        }
        catch (javax.ejb.EJBTransactionRolledbackException ex) {
            throw new javax.ejb.EJBTransactionRolledbackException("Er is geen betaalwijze opgegeven"); 
        }
       
        
        return betaling;
    }
    
    @PostConstruct
    public void init(){
        betaling = new Betaling();
        allBetaalwijzes = betaalwijzeFacade.findAll();
    }
    
}
