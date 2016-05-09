/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "factuur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factuur.findAll", query = "SELECT f FROM Factuur f"),
    @NamedQuery(name = "Factuur.findByIdFactuur", query = "SELECT f FROM Factuur f WHERE f.idFactuur = :idFactuur"),
    @NamedQuery(name = "Factuur.findByFactuurDatum", query = "SELECT f FROM Factuur f WHERE f.factuurDatum = :factuurDatum"),
    @NamedQuery(name = "Factuur.findByBestellingId", query = "SELECT f FROM Factuur f WHERE f.bestellingidBestelling = :idBestelling")})
public class Factuur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFactuur")
    private Integer idFactuur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "factuur_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date factuurDatum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factuuridFactuur")
    private Collection<Betaling> betalingCollection;
    @JoinColumn(name = "bestelling_idBestelling", referencedColumnName = "idBestelling")
    @ManyToOne(optional = false)
    private Bestelling bestellingidBestelling;

    public Factuur() {
    }

    public Factuur(Integer idFactuur) {
        this.idFactuur = idFactuur;
    }

    public Factuur(Integer idFactuur, Date factuurDatum) {
        this.idFactuur = idFactuur;
        this.factuurDatum = factuurDatum;
    }

    public Integer getIdFactuur() {
        return idFactuur;
    }

    public void setIdFactuur(Integer idFactuur) {
        this.idFactuur = idFactuur;
    }

    public Date getFactuurDatum() {
        return factuurDatum;
    }

    public void setFactuurDatum(Date factuurDatum) {
        this.factuurDatum = factuurDatum;
    }

    @XmlTransient
    public Collection<Betaling> getBetalingCollection() {
        return betalingCollection;
    }

    public void setBetalingCollection(Collection<Betaling> betalingCollection) {
        this.betalingCollection = betalingCollection;
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
        hash += (idFactuur != null ? idFactuur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factuur)) {
            return false;
        }
        Factuur other = (Factuur) object;
        if ((this.idFactuur == null && other.idFactuur != null) || (this.idFactuur != null && !this.idFactuur.equals(other.idFactuur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Factuur[ idFactuur=" + idFactuur + " ]";
    }
    
}
