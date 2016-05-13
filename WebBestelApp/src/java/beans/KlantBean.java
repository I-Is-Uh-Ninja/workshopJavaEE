/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Klant;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import session.KlantFacade;

/**
 *
 * @author Wytze Terpstra
 */
@Named
@Dependent
@Stateless
public class KlantBean implements Serializable{
    
    private Klant selectedKlant;
    private List<Klant> klanten;
    @EJB
    private KlantFacade klantFacade;
        
     public KlantBean() {
        selectedKlant = new Klant();
    }
     
    //=====Getters and Setters=========
     
     public List<Klant> getKlanten() {
         return klanten;
     }
     
     public void setKlanten(List<Klant> klanten) {
         this.klanten = klanten;
     }
     
    public Klant getSelectedKlant() {
         return selectedKlant;
     }
     
     public void setSelectedKlant(Klant selectedKlant) {
         this.selectedKlant = selectedKlant;
     }
     
     
    //=====Adding and removing from klant list===== 
     
     public void addToKlanten(Klant klant) {
         klantFacade.create(klant);
         klanten.add(klant);
     }
     
     public void removeFromKlanten(Klant klant) {
         int klantenListSize = klanten.size();
         int klantIndex = -1;
         for (int i = 0; i < klantenListSize; i++) {
             if (klanten.get(i) == klant) {
                 klantIndex = i;
             }
         }
         if (klantIndex != -1) {
             klantFacade.remove(klanten.get(klantIndex));
             klanten.remove(klantIndex);
         }
     }
     
     public void addThisKlant() {
         addToKlanten(selectedKlant);
         selectedKlant = new Klant();
     }
     
     public String editThisKlant() {
         klantFacade.edit(selectedKlant);
         selectedKlant = new Klant();
         return "klantlijst";
     }
     
      //=====Edit bestelling=====
     
     public String goToEditKlant(Klant klant) {
         setSelectedKlant(klant);
         return "editKlant";
     }
     
    
     //=====Other=====
     
     public Klant findKlantById(Integer id){
         return klantFacade.find(id);
     }
     
     @PostConstruct
     private void init() {
         setKlanten(klantFacade.findAll());
     }
}
