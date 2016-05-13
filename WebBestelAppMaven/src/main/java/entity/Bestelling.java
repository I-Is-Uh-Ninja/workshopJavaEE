/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "bestelling")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bestelling.findAll", query = "SELECT b FROM Bestelling b"),
    @NamedQuery(name = "Bestelling.findByIdBestelling", query = "SELECT b FROM Bestelling b WHERE b.idBestelling = :idBestelling"),
    @NamedQuery(name = "Bestelling.findByKlantId", query = "SELECT b FROM Bestelling b WHERE b.klantidKlant = :Klant_idKlant")})
public class Bestelling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBestelling")
    private Integer idBestelling;
    
    @JoinColumn(name = "Klant_idKlant", referencedColumnName = "idKlant")
    @ManyToOne(optional = false)
    private Klant klantidKlant;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bestellingidBestelling", fetch=FetchType.EAGER)
    private Collection<BestellingHasArtikel> bestellingHasArtikelCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bestellingidBestelling")
    private Collection<Factuur> factuurCollection;

    public Bestelling() {
    }

    public Bestelling(Integer idBestelling) {
        this.idBestelling = idBestelling;
    }

    public Integer getIdBestelling() {
        return idBestelling;
    }

    public void setIdBestelling(Integer idBestelling) {
        this.idBestelling = idBestelling;
    }

    public Klant getKlantidKlant() {
        return klantidKlant;
    }

    public void setKlantidKlant(Klant klantidKlant) {
        this.klantidKlant = klantidKlant;
    }

    @XmlTransient
    public Collection<BestellingHasArtikel> getBestellingHasArtikelCollection() {
        return bestellingHasArtikelCollection;
    }

    public void setBestellingHasArtikelCollection(Collection<BestellingHasArtikel> bestellingHasArtikelCollection) {
        this.bestellingHasArtikelCollection = bestellingHasArtikelCollection;
    }

    @XmlTransient
    public Collection<Factuur> getFactuurCollection() {
        return factuurCollection;
    }

    public void setFactuurCollection(Collection<Factuur> factuurCollection) {
        this.factuurCollection = factuurCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBestelling != null ? idBestelling.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestelling)) {
            return false;
        }
        Bestelling other = (Bestelling) object;
        if ((this.idBestelling == null && other.idBestelling != null) || (this.idBestelling != null && !this.idBestelling.equals(other.idBestelling))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Bestelling[ idBestelling=" + idBestelling + " ]";
    }
    
}
