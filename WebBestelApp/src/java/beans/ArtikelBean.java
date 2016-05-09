/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Artikel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import session.ArtikelFacade;


@Named
@Dependent
@Stateless
public class ArtikelBean implements Serializable {

    private List<Artikel> artikelen;
    @EJB
    private ArtikelFacade artikelFacade;
    private Artikel selectedArtikel;

    
    public ArtikelBean() {
        selectedArtikel = new Artikel();
    }

    public List<Artikel> getArtikelen() {
        return artikelen;
    }

    public void setArtikelen(List<Artikel> artikelen) {
        this.artikelen = artikelen;
    }
    
    public void addToArtikelen(Artikel artikel){
        artikelFacade.create(artikel);
        setArtikelen(artikelFacade.findAll());
    }
    
    public void removeFromArtikelen(Artikel artikel){
        int artikelenListSize = artikelen.size();
        int artikelIndex = -1;
        for (int i = 0; i < artikelenListSize; i++){
            if(artikelen.get(i).getIdArtikel() == artikel.getIdArtikel()){
                artikelIndex = i;
            }
        }
        if (artikelIndex != -1){
            artikelFacade.remove(artikelen.get(artikelIndex));
            artikelen.remove(artikelIndex);
        }
    }
    
    public void addThisArtikel(){
        addToArtikelen(selectedArtikel);
        selectedArtikel = new Artikel();
    }
    
    public String editThisArtikel(){
        artikelFacade.edit(selectedArtikel);
        selectedArtikel = new Artikel();
        return "artikellijst";
    }
    
    public String goToEditArtikel(Artikel artikel){
        setSelectedArtikel(artikel);
        return "editArtikel";
    }

    public Artikel getSelectedArtikel() {
        return selectedArtikel;
    }

    public void setSelectedArtikel(Artikel selectedArtikel) {
        this.selectedArtikel = selectedArtikel;
    }
    
    @PostConstruct
    private void init(){
        setArtikelen(artikelFacade.findAll());
    }
}
