/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Adres;
import entity.AdresType;
import entity.KlantHasAdres;
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
import session.AdresFacade;
import session.AdresTypeFacade;
import session.KlantFacade;
import session.KlantHasAdresFacade;

/*
AdresFacadeREST vervangt AdresTypeFacadeREST en KlantHasAdresFacadeREST
*/
@Stateless
@Path("adres")
public class AdresFacadeREST {

    @EJB
    AdresFacade adresFacade;
    
    @EJB
    AdresTypeFacade adresTypeFacade;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Adres entity) {
        adresFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Adres entity) {
        adresFacade.edit(entity);
    }
    
    //Adres should only be removed if no KlantHasAdres is attached
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        adresFacade.remove(adresFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Adres find(@PathParam("id") Integer id) {
        return adresFacade.find(id);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adres> findAll() {
        return adresFacade.findAll();
    }
    
    @GET
    @Path("adresType")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AdresType> findAllAdresTypes() {
        return adresTypeFacade.findAll();
    }
    
    @GET
    @Path("adresType/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AdresType findAdresType(@PathParam("id") Integer id) {
        return adresTypeFacade.find(id);
    }
    
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adres> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return adresFacade.findRange(new int[]{from, to});
    }
    */

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(adresFacade.count());
    }

}
