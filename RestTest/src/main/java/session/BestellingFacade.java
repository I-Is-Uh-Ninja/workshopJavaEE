/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Bestelling;
import entity.Factuur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ian
 */
@Stateless
public class BestellingFacade extends AbstractFacade<Bestelling> {

    @PersistenceContext(unitName = "WebBestelAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BestellingFacade() {
        super(Bestelling.class);
    }
    
    public List<Bestelling> findBestellingByKlantId(int klantId){
        Query query = em.createNamedQuery("Bestelling.findByKlantId", Bestelling.class);
        query = query.setParameter("Klant_idKlant", klantId);
        return query.getResultList();
    }
    
}
