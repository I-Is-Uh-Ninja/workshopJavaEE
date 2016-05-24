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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "klant_has_adres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KlantHasAdres.findAll", query = "SELECT k FROM KlantHasAdres k"),
    @NamedQuery(name = "KlantHasAdres.findByIdKlanthasadres", query = "SELECT k FROM KlantHasAdres k WHERE k.idKlanthasadres = :idKlanthasadres"),
    @NamedQuery(name = "KlantHasAdres.findByIdKlant", query = "SELECT k FROM KlantHasAdres k WHERE k.klantidKlant.idKlant = :idKlant"),
    @NamedQuery(name = "KlantHasAdres.findByIdAdres", query = "SELECT k FROM KlantHasAdres k WHERE k.adresidAdres.idAdres = :idAdres")})
public class KlantHasAdres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKlant_has_adres")
    private Integer idKlanthasadres;
    @JoinColumn(name = "adres_idAdres", referencedColumnName = "idAdres")
    @ManyToOne(optional = false)
    private Adres adresidAdres;
    @JoinColumn(name = "adres_type_idAdres_type", referencedColumnName = "idAdres_type")
    @ManyToOne(optional = false)
    private AdresType adrestypeidAdrestype;
    @JoinColumn(name = "klant_idKlant", referencedColumnName = "idKlant")
    @ManyToOne(optional = false)
    private Klant klantidKlant;

    public KlantHasAdres() {
    }

    public KlantHasAdres(Integer idKlanthasadres) {
        this.idKlanthasadres = idKlanthasadres;
    }

    public Integer getIdKlanthasadres() {
        return idKlanthasadres;
    }

    public void setIdKlanthasadres(Integer idKlanthasadres) {
        this.idKlanthasadres = idKlanthasadres;
    }

    public Adres getAdresidAdres() {
        return adresidAdres;
    }

    public void setAdresidAdres(Adres adresidAdres) {
        this.adresidAdres = adresidAdres;
    }

    public AdresType getAdrestypeidAdrestype() {
        return adrestypeidAdrestype;
    }

    public void setAdrestypeidAdrestype(AdresType adrestypeidAdrestype) {
        this.adrestypeidAdrestype = adrestypeidAdrestype;
    }

    public Klant getKlantidKlant() {
        return klantidKlant;
    }

    public void setKlantidKlant(Klant klantidKlant) {
        this.klantidKlant = klantidKlant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKlanthasadres != null ? idKlanthasadres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KlantHasAdres)) {
            return false;
        }
        KlantHasAdres other = (KlantHasAdres) object;
        if ((this.idKlanthasadres == null && other.idKlanthasadres != null) || (this.idKlanthasadres != null && !this.idKlanthasadres.equals(other.idKlanthasadres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.KlantHasAdres[ idKlanthasadres=" + idKlanthasadres + " ]";
    }
    
}
