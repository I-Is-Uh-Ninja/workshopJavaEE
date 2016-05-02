/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "bestelling_has_artikel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BestellingHasArtikel.findAll", query = "SELECT b FROM BestellingHasArtikel b"),
    @NamedQuery(name = "BestellingHasArtikel.findByIdBestelArtikel", query = "SELECT b FROM BestellingHasArtikel b WHERE b.idBestelArtikel = :idBestelArtikel"),
    @NamedQuery(name = "BestellingHasArtikel.findByAantal", query = "SELECT b FROM BestellingHasArtikel b WHERE b.aantal = :aantal")})
public class BestellingHasArtikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBestelArtikel")
    private Integer idBestelArtikel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aantal")
    private int aantal;
    @JoinColumn(name = "artikel_idArtikel", referencedColumnName = "idArtikel")
    @ManyToOne(optional = false)
    private Artikel artikelidArtikel;
    @JoinColumn(name = "bestelling_idBestelling", referencedColumnName = "idBestelling")
    @ManyToOne(optional = false)
    private Bestelling bestellingidBestelling;

    public BestellingHasArtikel() {
    }

    public BestellingHasArtikel(Integer idBestelArtikel) {
        this.idBestelArtikel = idBestelArtikel;
    }

    public BestellingHasArtikel(Integer idBestelArtikel, int aantal) {
        this.idBestelArtikel = idBestelArtikel;
        this.aantal = aantal;
    }

    public Integer getIdBestelArtikel() {
        return idBestelArtikel;
    }

    public void setIdBestelArtikel(Integer idBestelArtikel) {
        this.idBestelArtikel = idBestelArtikel;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public Artikel getArtikelidArtikel() {
        return artikelidArtikel;
    }

    public void setArtikelidArtikel(Artikel artikelidArtikel) {
        this.artikelidArtikel = artikelidArtikel;
    }

    public Bestelling getBestellingidBestelling() {
        return bestellingidBestelling;
    }

    public void setBestellingidBestelling(Bestelling bestellingidBestelling) {
        this.bestellingidBestelling = bestellingidBestelling;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBestelArtikel != null ? idBestelArtikel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BestellingHasArtikel)) {
            return false;
        }
        BestellingHasArtikel other = (BestellingHasArtikel) object;
        if ((this.idBestelArtikel == null && other.idBestelArtikel != null) || (this.idBestelArtikel != null && !this.idBestelArtikel.equals(other.idBestelArtikel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.BestellingHasArtikel[ idBestelArtikel=" + idBestelArtikel + " ]";
    }
    
}
