/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "betaling")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Betaling.findAll", query = "SELECT b FROM Betaling b"),
    @NamedQuery(name = "Betaling.findByIdBetaling", query = "SELECT b FROM Betaling b WHERE b.idBetaling = :idBetaling"),
    @NamedQuery(name = "Betaling.findByBetaalDatum", query = "SELECT b FROM Betaling b WHERE b.betaalDatum = :betaalDatum"),
    @NamedQuery(name = "Betaling.findByBetalingsGegevens", query = "SELECT b FROM Betaling b WHERE b.betalingsGegevens = :betalingsGegevens")})
public class Betaling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBetaling")
    private Integer idBetaling;
    @Basic(optional = false)
    @NotNull
    @Column(name = "betaal_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date betaalDatum;
    @Size(max = 80)
    @Column(name = "betalingsGegevens")
    private String betalingsGegevens;
    @JoinColumn(name = "betaalwijze_idBetaalwijze", referencedColumnName = "idBetaalwijze")
    @ManyToOne(optional = false)
    private Betaalwijze betaalwijzeidBetaalwijze;
    @JoinColumn(name = "factuur_idFactuur", referencedColumnName = "idFactuur")
    @ManyToOne(optional = false)
    private Factuur factuuridFactuur;
    @JoinColumn(name = "klant_idKlant", referencedColumnName = "idKlant")
    @ManyToOne(optional = false)
    private Klant klantidKlant;

    public Betaling() {
    }

    public Betaling(Integer idBetaling) {
        this.idBetaling = idBetaling;
    }

    public Betaling(Integer idBetaling, Date betaalDatum) {
        this.idBetaling = idBetaling;
        this.betaalDatum = betaalDatum;
    }

    public Integer getIdBetaling() {
        return idBetaling;
    }

    public void setIdBetaling(Integer idBetaling) {
        this.idBetaling = idBetaling;
    }

    public Date getBetaalDatum() {
        return betaalDatum;
    }

    public void setBetaalDatum(Date betaalDatum) {
        this.betaalDatum = betaalDatum;
    }

    public String getBetalingsGegevens() {
        return betalingsGegevens;
    }

    public void setBetalingsGegevens(String betalingsGegevens) {
        this.betalingsGegevens = betalingsGegevens;
    }

    public Betaalwijze getBetaalwijzeidBetaalwijze() {
        return betaalwijzeidBetaalwijze;
    }

    public void setBetaalwijzeidBetaalwijze(Betaalwijze betaalwijzeidBetaalwijze) {
        this.betaalwijzeidBetaalwijze = betaalwijzeidBetaalwijze;
    }

    public Factuur getFactuuridFactuur() {
        return factuuridFactuur;
    }

    public void setFactuuridFactuur(Factuur factuuridFactuur) {
        this.factuuridFactuur = factuuridFactuur;
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
        hash += (idBetaling != null ? idBetaling.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Betaling)) {
            return false;
        }
        Betaling other = (Betaling) object;
        if ((this.idBetaling == null && other.idBetaling != null) || (this.idBetaling != null && !this.idBetaling.equals(other.idBetaling))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Betaling[ idBetaling=" + idBetaling + " ]";
    }
    
}
