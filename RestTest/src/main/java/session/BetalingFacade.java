/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Betaling;
import entity.Factuur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ian
 */
@Stateless
public class BetalingFacade extends AbstractFacade<Betaling> {

    @PersistenceContext(unitName = "RestTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BetalingFacade() {
        super(Betaling.class);
    }
    
    public List<Betaling> findByFactuur(int factuurId){
        Query query = em.createNamedQuery("Betaling.findByFactuur", Betaling.class);
        query = query.setParameter("idFactuur", factuurId);
        return query.getResultList();
    }
    
}
