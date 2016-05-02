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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "adres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adres.findAll", query = "SELECT a FROM Adres a"),
    @NamedQuery(name = "Adres.findByIdAdres", query = "SELECT a FROM Adres a WHERE a.idAdres = :idAdres"),
    @NamedQuery(name = "Adres.findByStraatnaam", query = "SELECT a FROM Adres a WHERE a.straatnaam = :straatnaam"),
    @NamedQuery(name = "Adres.findByPostcode", query = "SELECT a FROM Adres a WHERE a.postcode = :postcode"),
    @NamedQuery(name = "Adres.findByHuisnummer", query = "SELECT a FROM Adres a WHERE a.huisnummer = :huisnummer"),
    @NamedQuery(name = "Adres.findByWoonplaats", query = "SELECT a FROM Adres a WHERE a.woonplaats = :woonplaats")})
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdres")
    private Integer idAdres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 34)
    @Column(name = "straatnaam")
    private String straatnaam;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "postcode")
    private String postcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "huisnummer")
    private String huisnummer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "woonplaats")
    private String woonplaats;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adresidAdres")
    private Collection<KlantHasAdres> klantHasAdresCollection;

    public Adres() {
    }

    public Adres(Integer idAdres) {
        this.idAdres = idAdres;
    }

    public Adres(Integer idAdres, String straatnaam, String postcode, String huisnummer, String woonplaats) {
        this.idAdres = idAdres;
        this.straatnaam = straatnaam;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.woonplaats = woonplaats;
    }

    public Integer getIdAdres() {
        return idAdres;
    }

    public void setIdAdres(Integer idAdres) {
        this.idAdres = idAdres;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    @XmlTransient
    public Collection<KlantHasAdres> getKlantHasAdresCollection() {
        return klantHasAdresCollection;
    }

    public void setKlantHasAdresCollection(Collection<KlantHasAdres> klantHasAdresCollection) {
        this.klantHasAdresCollection = klantHasAdresCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdres != null ? idAdres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adres)) {
            return false;
        }
        Adres other = (Adres) object;
        if ((this.idAdres == null && other.idAdres != null) || (this.idAdres != null && !this.idAdres.equals(other.idAdres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Adres[ idAdres=" + idAdres + " ]";
    }
    
}
