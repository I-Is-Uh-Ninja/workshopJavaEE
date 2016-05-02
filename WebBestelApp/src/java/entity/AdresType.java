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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "adres_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdresType.findAll", query = "SELECT a FROM AdresType a"),
    @NamedQuery(name = "AdresType.findByIdAdrestype", query = "SELECT a FROM AdresType a WHERE a.idAdrestype = :idAdrestype"),
    @NamedQuery(name = "AdresType.findByAdresType", query = "SELECT a FROM AdresType a WHERE a.adresType = :adresType")})
public class AdresType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdres_type")
    private Integer idAdrestype;
    @Size(max = 45)
    @Column(name = "adres_type")
    private String adresType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adrestypeidAdrestype")
    private Collection<KlantHasAdres> klantHasAdresCollection;

    public AdresType() {
    }

    public AdresType(Integer idAdrestype) {
        this.idAdrestype = idAdrestype;
    }

    public Integer getIdAdrestype() {
        return idAdrestype;
    }

    public void setIdAdrestype(Integer idAdrestype) {
        this.idAdrestype = idAdrestype;
    }

    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
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
        hash += (idAdrestype != null ? idAdrestype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdresType)) {
            return false;
        }
        AdresType other = (AdresType) object;
        if ((this.idAdrestype == null && other.idAdrestype != null) || (this.idAdrestype != null && !this.idAdrestype.equals(other.idAdrestype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.AdresType[ idAdrestype=" + idAdrestype + " ]";
    }
    
}
