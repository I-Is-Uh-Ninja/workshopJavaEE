/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Artikel;
import entity.Bestelling;
import entity.BestellingHasArtikel;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import session.ArtikelFacade;
import session.BestellingFacade;
import session.BestellingHasArtikelFacade;
import session.KlantFacade;

/**
 *
 * @author BAM
 */

//=== Bestelling CRUD behouden
//=== Toevoegen/verwijderen/lezen van BestellingHasArtikel aan code toevoegen

@Stateless
@Path("bestelling")
public class BestellingFacadeREST {

    @EJB
    BestellingFacade bestellingFacade;
    
    @EJB
    KlantFacade klantFacade;
    
    @EJB
    ArtikelFacade artikelFacade;
    
    @EJB
    BestellingHasArtikelFacade bhaf;
    
    
    @POST   
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Bestelling entity) {
        bestellingFacade.create(entity);
    }
    
    //Voegt artikel toe door bha toe te voegen
    @POST
    @Path("addArtikel")
    @Consumes({MediaType.APPLICATION_JSON})
    public void addArtikel(BestellingHasArtikel entity) {
        bhaf.create(entity);
    }
    
    //verwijderd artikel uit bestelling door de bha te verwijderen, kan ook aan de hand van bha id? 
    @DELETE
    @Path("removeArtikel")
    @Consumes({MediaType.APPLICATION_JSON})
    public void removeArtikel(BestellingHasArtikel entity) {
         bhaf.remove(entity);
    }
    
    //Bestelling heeft alleen bestellingId en klantId, beide hoeven niet te worden aangepast
    /*@PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Bestelling entity) {
        bestellingFacade.edit(entity);
    }*/

    //delete aan de hand van de bestellign ID
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        bestellingFacade.remove(bestellingFacade.find(id));
    }
    
    //vindt bestelling aan de hand van id 
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Bestelling find(@PathParam("id") Integer id) {
        return bestellingFacade.find(id);
    }
    
    //vindt artikelen in een bestelling
    @GET
    @Path("artikellijstbestelling/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artikel> findArtikelInBestelling() {
         return artikelFacade.findAll();
    }
    
    //vindt lijst met bestellingen
    @GET
    @Path("bestellinglijst")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bestelling> findAll() {
        return bestellingFacade.findAll();
    }
    
    //vindt bestellingen bij deze klant
    @GET
    @Path("bestellingByKlant/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bestelling> findAllByKlant(@PathParam("id") Integer klantId) {
        return bestellingFacade.findBestellingByKlantId(klantId);
    }
    
    //niet nodig
    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bestelling> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return bestellingFacade.findRange(new int[]{from, to});
    }*/

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(bestellingFacade.count());
    }
}
