/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Adres;
import entity.AdresType;
import entity.Klant;
import entity.KlantHasAdres;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import session.AdresFacade;
import session.AdresTypeFacade;
import session.KlantHasAdresFacade;

/**
 *
 * @author Wytze Terpstra
 */
@Named
@Dependent
@Stateless
public class AdresBean implements Serializable {
   
    private Adres selectedAdres;
    private AdresType selectedAdresType;
    private Klant selectedKlant;
    private List<Adres> adressen;
    private List<KlantHasAdres> adressenInKlant;
    private KlantHasAdres selectedAdresInKlant;
    private Integer klantId;
    private List<AdresType> allAdresTypes;
    
    @EJB
    private AdresFacade adresFacade;
    
    @EJB
    private KlantHasAdresFacade kHAFacade;
    
    @EJB
    private AdresTypeFacade adresTypeFacade;
        
    public AdresBean() {
        selectedAdres = new Adres();
        selectedAdresType = new AdresType();
        selectedKlant = new Klant();
    }
     //=====Getters and Setters=========     
    
    public List<Adres> getAdressen() {
        return adressen;
    } 
        
    public void setAdressen(List<Adres> adressen) {
        this.adressen = adressen;
    }
        
    public Adres getSelectedAdres() {
        return selectedAdres;
    }
    
    public void setSelectedAdres(Adres selectedAdres) {
        this.selectedAdres = selectedAdres;
    }

    public List<KlantHasAdres> getAdressenInKlant() {
        return adressenInKlant;
    }

    public void setAdressenInKlant(List<KlantHasAdres> adressenInKlant) {
        this.adressenInKlant = adressenInKlant;
    }

    public KlantHasAdres getSelectedAdresInKlant() {
        return selectedAdresInKlant;
    }

    public void setSelectedAdresInKlant(KlantHasAdres selectedAdresInKlant) {
        this.selectedAdresInKlant = selectedAdresInKlant;
    }

    public Integer getKlantId() {
        return klantId;
    }

    public void setKlantId(Integer klantId) {
        this.klantId = klantId;
    }

    public AdresType getSelectedAdresType() {
        return selectedAdresType;
    }

    public void setSelectedAdresType(AdresType selectedAdresType) {
        this.selectedAdresType = selectedAdresType;
    }

    public List<AdresType> getAllAdresTypes() {
        return allAdresTypes;
    }

    public void setAllAdresTypes(List<AdresType> allAdresTypes) {
        this.allAdresTypes = allAdresTypes;
    }

    public Klant getSelectedKlant() {
        return selectedKlant;
    }

    public void setSelectedKlant(Klant selectedKlant) {
        this.selectedKlant = selectedKlant;
    }
    
    
     //=====Adding and removing from adres list=====
    
    
    //Adding a new adres and go to addExistingAdres
    public String addToAdressen() {
        adresFacade.create(selectedAdres);
        adressen.add(selectedAdres);
        addExistingAdres(selectedAdres);
        return "viewKlant";
    }
    
    //Add an existing adres to a klant
    public String addExistingAdres(Adres adres){
        KlantHasAdres kha = new KlantHasAdres();
        kha.setAdresidAdres(adres);
        setSelectedAdresType(adresTypeFacade.find(selectedAdresType.getIdAdrestype()));
        kha.setAdrestypeidAdrestype(selectedAdresType);
        kha.setKlantidKlant(selectedKlant);
        kHAFacade.create(kha);
        adressenInKlant.add(kha);
        selectedAdres = new Adres();
        selectedAdresType = new AdresType();
        return "viewKlant";
    }
    
    //Remove an adres from klant, then check if the adres is not connected to another klant. If it is not connected, delete it entirely
    public void removeAdresFromKlant(KlantHasAdres adresInKlant){
        kHAFacade.remove(adresInKlant);
        List<KlantHasAdres> kha = kHAFacade.findByIdAdres(adresInKlant.getAdresidAdres().getIdAdres());
        if(kha.isEmpty()){
            removeFromAdressen(adresInKlant.getAdresidAdres());
        }
        setAdressenInKlant(kHAFacade.findByIdKlant(adresInKlant.getKlantidKlant().getIdKlant()));
        if (adressenInKlant == null) {
            setAdressenInKlant(new ArrayList<KlantHasAdres>());
        }
        
        setAdressen(adresFacade.findAll());
    }
    
    //Remove an adres entirely
    public void removeFromAdressen(Adres adres) {/*
        int adressenListSize = adressen.size();
        int adresIndex = -1;
        for (int i = 0; i < adressenListSize; i++) {
            if (adressen.get(i) == adres) {
                adresIndex = i;
            }          
        }
        if (adresIndex != -1) {
                adresFacade.remove(adressen.get(adresIndex));
                adressen.remove(adresIndex);
        }*/
        adresFacade.remove(adres);
    } 
    
    //=====Edit adres=====
    
    //Edit the adres
    public void editAdres() {
        adresFacade.edit(selectedAdres);
        selectedAdres = new Adres();
    }
    
    //Edit the KlantHasAdres
    public String editAdresInKlant(){
        selectedAdresInKlant.setAdresidAdres(selectedAdres);
        editAdres();
        selectedAdresInKlant.setAdrestypeidAdrestype(adresTypeFacade.find(selectedAdresType.getIdAdrestype()));
        kHAFacade.edit(selectedAdresInKlant);
        selectedAdresInKlant = new KlantHasAdres();
        setAdressen(adresFacade.findAll());
        return "viewKlant";
    }
    
    //Set selected adres en klanthasadres, and go to the "editAdres" page
    public String goToEditAdres(KlantHasAdres adresInKlant) {
        setSelectedAdresInKlant(adresInKlant);
        setSelectedAdres(adresInKlant.getAdresidAdres());
        return "editAdres";
    }
    
    //=====Other=====
    
    //find all adressen from a klant
    public void findAdressenByKlant(Klant klant){
        setSelectedKlant(klant);
        setAdressenInKlant(kHAFacade.findByIdKlant(klant.getIdKlant()));
    }
    
    @PostConstruct
    private void init() {
        setAdressen(adresFacade.findAll());
        setAllAdresTypes(adresTypeFacade.findAll());
    }
}
