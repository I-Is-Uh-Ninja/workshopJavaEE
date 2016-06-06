/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.KlantHasAdres;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import session.AdresFacade;
import session.KlantHasAdresFacade;

/**
 *
 * @author Ian
 */
@Stateless
@Path("klant/{klantId}/adres")
public class KlantHasAdresFacadeREST {

    @EJB
    KlantHasAdresFacade klantHasAdresFacade;
    
    @EJB
    AdresFacade adresFacade;
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(KlantHasAdres entity) {
        klantHasAdresFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, KlantHasAdres entity) {
        klantHasAdresFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        KlantHasAdres toRemove = klantHasAdresFacade.find(id);
        int adresId = toRemove.getAdresidAdres().getIdAdres();
        klantHasAdresFacade.remove(toRemove);
        if(klantHasAdresFacade.findByIdAdres(adresId).isEmpty()){
            adresFacade.remove(adresFacade.find(adresId));
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public KlantHasAdres find(@PathParam("id") Integer id) {
        return klantHasAdresFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<KlantHasAdres> findAll(@PathParam("klantId") Integer klantId) {
        return klantHasAdresFacade.findByIdKlant(klantId);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(klantHasAdresFacade.count());
    }
    
}
