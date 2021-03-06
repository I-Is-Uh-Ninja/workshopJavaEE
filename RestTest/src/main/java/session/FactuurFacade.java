/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

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
public class FactuurFacade extends AbstractFacade<Factuur> {

    @PersistenceContext(unitName = "RestTestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Integer createWithId(Factuur entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity.getIdFactuur();
    }

    public FactuurFacade() {
        super(Factuur.class);
    }
    
    public List<Factuur> findFactuurByBestellingId(int bestellingId){
        Query query = em.createNamedQuery("Factuur.findByBestellingId", Factuur.class);
        query = query.setParameter("idBestelling", bestellingId);
        return query.getResultList();
    }
}
