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
@Path("klant/{klantId}/adres")
public class AdresFacadeREST {

    @EJB
    AdresFacade adresFacade;
    
    @EJB
    AdresTypeFacade adresTypeFacade;
    
    @EJB
    KlantHasAdresFacade khaFacade;
    
    @EJB
    KlantFacade klantFacade;

    @POST
    @Path("nieuwAdres")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Adres entity) {
        adresFacade.create(entity);
    }
    
    @POST
    @Path("existingAdres")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addExistingAdres(KlantHasAdres entity) {
        khaFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, KlantHasAdres entity) {
        khaFacade.edit(entity);
    }
    
    //Adres should only be removed if no KlantHasAdres is attached
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id, @PathParam("klantId") Integer klantId) {
        Integer idAdres = khaFacade.find(id).getAdresidAdres().getIdAdres();
        khaFacade.remove(khaFacade.find(id));
        if(khaFacade.findByIdAdres(idAdres).isEmpty()){
            adresFacade.remove(adresFacade.find(idAdres));
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public KlantHasAdres find(@PathParam("id") Integer id) {
        return khaFacade.find(id);
    }
    
    //find all for this klant
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<KlantHasAdres> findAll(@PathParam("klantId") Integer klantId) {
        return khaFacade.findByIdKlant(klantId);
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
