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
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByIdAccount", query = "SELECT a FROM Account a WHERE a.idAccount = :idAccount"),
    @NamedQuery(name = "Account.findByAccountNaam", query = "SELECT a FROM Account a WHERE a.accountNaam = :accountNaam"),
    @NamedQuery(name = "Account.findByCreatieDatum", query = "SELECT a FROM Account a WHERE a.creatieDatum = :creatieDatum"),
    @NamedQuery(name = "Account.findAccountByKlantId", query = "SELECT a FROM Account a WHERE a.klantidKlant.idKlant = :idKlant")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAccount")
    private Integer idAccount;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "account_naam")
    private String accountNaam;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "creatie_datum")
    @Temporal(TemporalType.TIMESTAMP) //was TemporalType.DATE
    private Date creatieDatum;
    
    @JoinColumn(name = "klant_idKlant", referencedColumnName = "idKlant")
    @ManyToOne(optional = false)
    private Klant klantidKlant;

    public Account() {
    }

    public Account(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public Account(Integer idAccount, String accountNaam, Date creatieDatum) {
        this.idAccount = idAccount;
        this.accountNaam = accountNaam;
        this.creatieDatum = creatieDatum;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountNaam() {
        return accountNaam;
    }

    public void setAccountNaam(String accountNaam) {
        this.accountNaam = accountNaam;
    }

    public Date getCreatieDatum() {
        return creatieDatum;
    }

    public void setCreatieDatum(Date creatieDatum) {
        this.creatieDatum = creatieDatum;
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
        hash += (idAccount != null ? idAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.idAccount == null && other.idAccount != null) || (this.idAccount != null && !this.idAccount.equals(other.idAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Account[ idAccount=" + idAccount + " ]";
    }
    
}
