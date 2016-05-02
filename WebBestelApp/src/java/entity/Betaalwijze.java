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
@Table(name = "betaalwijze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Betaalwijze.findAll", query = "SELECT b FROM Betaalwijze b"),
    @NamedQuery(name = "Betaalwijze.findByIdBetaalwijze", query = "SELECT b FROM Betaalwijze b WHERE b.idBetaalwijze = :idBetaalwijze"),
    @NamedQuery(name = "Betaalwijze.findByBetaalwijze", query = "SELECT b FROM Betaalwijze b WHERE b.betaalwijze = :betaalwijze")})
public class Betaalwijze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBetaalwijze")
    private Integer idBetaalwijze;
    @Size(max = 80)
    @Column(name = "betaalwijze")
    private String betaalwijze;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betaalwijzeidBetaalwijze")
    private Collection<Betaling> betalingCollection;

    public Betaalwijze() {
    }

    public Betaalwijze(Integer idBetaalwijze) {
        this.idBetaalwijze = idBetaalwijze;
    }

    public Integer getIdBetaalwijze() {
        return idBetaalwijze;
    }

    public void setIdBetaalwijze(Integer idBetaalwijze) {
        this.idBetaalwijze = idBetaalwijze;
    }

    public String getBetaalwijze() {
        return betaalwijze;
    }

    public void setBetaalwijze(String betaalwijze) {
        this.betaalwijze = betaalwijze;
    }

    @XmlTransient
    public Collection<Betaling> getBetalingCollection() {
        return betalingCollection;
    }

    public void setBetalingCollection(Collection<Betaling> betalingCollection) {
        this.betalingCollection = betalingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBetaalwijze != null ? idBetaalwijze.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Betaalwijze)) {
            return false;
        }
        Betaalwijze other = (Betaalwijze) object;
        if ((this.idBetaalwijze == null && other.idBetaalwijze != null) || (this.idBetaalwijze != null && !this.idBetaalwijze.equals(other.idBetaalwijze))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Betaalwijze[ idBetaalwijze=" + idBetaalwijze + " ]";
    }
    
}
