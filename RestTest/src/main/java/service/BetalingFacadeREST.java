/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Betaalwijze;
import entity.Betaling;
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
import session.BetaalwijzeFacade;
import session.BetalingFacade;

/**
 *
 * @author BAM
 */
//=== XMLcode kan weg 
//=== Toevoegen/lezen van Betaling aan code toevoegen
//=== Lezen van Betaalwijze toevoegen en selecteer maken in een Betaling
//=== Factuur aan betaling toevoegen

@Stateless
@Path("betaling")
public class BetalingFacadeREST {

    @EJB
    BetalingFacade betalingFacade;
    
    @EJB
    BetaalwijzeFacade betaalwijzeFacade;
            
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Betaling entity) {
        betalingFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Betaling entity) {
        betalingFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        betalingFacade.remove(betalingFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Betaling find(@PathParam("id") Integer id) {
        return betalingFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Betaling> findAll() {
        return betalingFacade.findAll();
    }
    
    @GET
    @Path("betaalwijze")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Betaalwijze> findAllBetaalwijzes() {
        return betaalwijzeFacade.findAll();
    }
    
    @GET
    @Path("betaalwijze/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Betaalwijze findBetaalwijze(@PathParam("id") Integer id) {
        return betaalwijzeFacade.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Betaling> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return betalingFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(betalingFacade.count());
    }    
}
