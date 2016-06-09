/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BestellingHasArtikel;
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
public class BestellingHasArtikelFacade extends AbstractFacade<BestellingHasArtikel> {

    @PersistenceContext(unitName = "WebBestelAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BestellingHasArtikelFacade() {
        super(BestellingHasArtikel.class);
    }
    
    public List<BestellingHasArtikel> findArtikelenInBestelling(int bestellingId){
        Query query = em.createNamedQuery("BestellingHasArtikel.findByBestellingId", BestellingHasArtikel.class);
        query = query.setParameter("bestelling_idBestelling", bestellingId);
        return query.getResultList();
    }
           
    public List<BestellingHasArtikel> findByIdKlant(Integer idKlant){
        Query query = em.createNamedQuery("BestellingHasArtikel.findByIdKlant", BestellingHasArtikel.class);
        query.setParameter("idKlant", idKlant);
        return query.getResultList();
    }
    
    public List<BestellingHasArtikel> findByBestellingId(Integer idBestelling){
        Query query = em.createNamedQuery("BestellingHasArtikel.findByBestellingId", BestellingHasArtikel.class);
        query.setParameter("bestelling_idBestelling", idBestelling);
        return query.getResultList();
    }
}
