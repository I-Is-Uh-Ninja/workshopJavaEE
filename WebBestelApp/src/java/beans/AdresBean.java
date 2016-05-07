/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Adres;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import session.AdresFacade;

/**
 *
 * @author Wytze Terpstra
 */
@Named
@Dependent
@Stateless
public class AdresBean implements Serializable {
    String title;
    Adres selectedAdres;
    List<Adres> adressen;
    @EJB
    AdresFacade adresFacade;
        
    public AdresBean() {
        selectedAdres = new Adres();
        title = "JSF managed AdresBean";
    }
         
    public List<Adres> getAdressen() {
        return adressen;
    } 
    
    public void setAdressen(List<Adres> adressen) {
        this.adressen = adressen;
    }
    
    public void addToAdressen(Adres adres) {
        adresFacade.create(adres);
        adressen.add(adres);
    }
    
    public void removeFromAdressen(Adres adres) {
        int adressenListSize = adressen.size();
        int adresIndex = -1;
        for (int i = 0; i < adressenListSize; i++) {
            if (adressen.get(i).getIdAdres() == adres.getIdAdres()) {
                adresIndex = i;
            }          
        }
        if (adresIndex != -1) {
                adresFacade.remove(adressen.get(adresIndex));
                adressen.remove(adresIndex);
        }
    } 
    
    public void addThisAdres() {
            addToAdressen(selectedAdres);
            selectedAdres = new Adres();
        }
    
    public String editThisAdres() {
        adresFacade.edit(selectedAdres);
        selectedAdres = new Adres();
        return "adresIndex";
    }
    
    public String goToEditAdres(Adres adres) {
        setSelectedAdres(adres);
        return "editAdres";
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Adres getSelectedAdres() {
        return selectedAdres;
    }
    
    public void setSelectedAdres(Adres selectedAdres) {
        this.selectedAdres = selectedAdres;
    }
    
    @PostConstruct
    private void init() {
        setAdressen(adresFacade.findAll());
    }
}
