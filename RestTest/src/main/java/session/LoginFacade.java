/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Login;
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
public class LoginFacade extends AbstractFacade<Login> {

    @PersistenceContext(unitName = "WebBestelAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }
    
    public List<Login> findByInlognaam(String inlognaam){
        Query query = em.createNamedQuery("Login.findByInlognaam", Login.class);
        query = query.setParameter("inlognaam", inlognaam);
        return query.getResultList();
    }
    
}
