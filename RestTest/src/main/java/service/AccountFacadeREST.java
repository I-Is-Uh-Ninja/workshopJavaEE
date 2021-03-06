/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Account;
import entity.Klant;
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
import session.AccountFacade;
import session.KlantFacade;

/**
 *
 * @author BAM
 */
@Stateless
@Path("klant/{klantId}/account")
public class AccountFacadeREST {

    @EJB
    AccountFacade accountFacade;
    
    @EJB
    KlantFacade klantFacade;
    
    private Klant selectedKlant;
    
    public void setSelectedKlant(Integer klantId){
        this.selectedKlant = klantFacade.find(klantId);
    }
    
    public Klant getSelectedKlant(){
        return this.selectedKlant;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Account entity, @PathParam("klantId") Integer klantId) {
        setSelectedKlant(klantId);
        entity.setKlantidKlant(selectedKlant);
        accountFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Account entity) {
        accountFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        accountFacade.remove(accountFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account find(@PathParam("id") Integer id) {
        return accountFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> findAllByKlant(@PathParam("klantId") Integer id) {
        return accountFacade.findAccountByKlantId(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return accountFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(accountFacade.count());
    }    
}
