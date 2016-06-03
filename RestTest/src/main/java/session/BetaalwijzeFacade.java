/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Betaalwijze;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ian
 */
@Stateless
public class BetaalwijzeFacade extends AbstractFacade<Betaalwijze> {

    @PersistenceContext(unitName = "WebBestelAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BetaalwijzeFacade() {
        super(Betaalwijze.class);
    }
    
}
