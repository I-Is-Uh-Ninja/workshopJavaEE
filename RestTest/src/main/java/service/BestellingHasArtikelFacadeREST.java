/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Artikel;
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
import session.BestellingHasArtikelFacade;

/**
 *
 * @author BAM
 */
@Stateless
@Path("bestellinghasartikel")
public class BestellingHasArtikelFacadeREST {

   @EJB
   BestellingHasArtikelFacade bhaf;
  
   
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(BestellingHasArtikel entity) {
        bhaf.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, BestellingHasArtikel entity) {
        bhaf.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        bhaf.remove(bhaf.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BestellingHasArtikel> find(@PathParam("id") Integer id) {
        return bhaf.findByBestellingId(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BestellingHasArtikel> findAll() {
        return bhaf.findAll();
    }
           

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BestellingHasArtikel> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return bhaf.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(bhaf.count());
    }    
}
