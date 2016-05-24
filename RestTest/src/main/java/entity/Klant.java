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
@Table(name = "klant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klant.findAll", query = "SELECT k FROM Klant k"),
    @NamedQuery(name = "Klant.findByIdKlant", query = "SELECT k FROM Klant k WHERE k.idKlant = :idKlant"),
    @NamedQuery(name = "Klant.findByVoornaam", query = "SELECT k FROM Klant k WHERE k.voornaam = :voornaam"),
    @NamedQuery(name = "Klant.findByTussenvoegsel", query = "SELECT k FROM Klant k WHERE k.tussenvoegsel = :tussenvoegsel"),
    @NamedQuery(name = "Klant.findByAchternaam", query = "SELECT k FROM Klant k WHERE k.achternaam = :achternaam"),
    @NamedQuery(name = "Klant.findByEmail", query = "SELECT k FROM Klant k WHERE k.email = :email")})
public class Klant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKlant")
    private Integer idKlant;
    @Basic(optional = false)
    @NotNull(message="De klant met een voornaam hebben")
    @Size(min = 1, max = 50, message="De voornaam moet minimaal 1 en maximaal 50 karakters lang zijn")
    @Column(name = "voornaam")
    private String voornaam;
    @Size(max = 10, message="Het tussenvoegsel mag maximaal 10 karakters lang zijn.")
    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;
    @Basic(optional = false)
    @NotNull(message="De klant moet een achternaam hebben")
    @Size(min = 1, max = 51, message="De achternaam moet minimaal 1 en maximaal 51 karakters lang zijn")
    @Column(name = "achternaam")
    private String achternaam;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull(message="De klant moet een emailadres opgeven")
    @Size(min = 1, max = 80, message="Het emailadres moet minimaal 1 en maximaal 80 karakters lang zijn")
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantidKlant")
    private Collection<Betaling> betalingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantidKlant")
    private Collection<Bestelling> bestellingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantidKlant")
    private Collection<Account> accountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantidKlant")
    private Collection<KlantHasAdres> klantHasAdresCollection;

    public Klant() {
    }

    public Klant(Integer idKlant) {
        this.idKlant = idKlant;
    }

    public Klant(Integer idKlant, String voornaam, String achternaam, String email) {
        this.idKlant = idKlant;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public Integer getIdKlant() {
        return idKlant;
    }

    public void setIdKlant(Integer idKlant) {
        this.idKlant = idKlant;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Betaling> getBetalingCollection() {
        return betalingCollection;
    }

    public void setBetalingCollection(Collection<Betaling> betalingCollection) {
        this.betalingCollection = betalingCollection;
    }

    @XmlTransient
    public Collection<Bestelling> getBestellingCollection() {
        return bestellingCollection;
    }

    public void setBestellingCollection(Collection<Bestelling> bestellingCollection) {
        this.bestellingCollection = bestellingCollection;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
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
        hash += (idKlant != null ? idKlant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klant)) {
            return false;
        }
        Klant other = (Klant) object;
        if ((this.idKlant == null && other.idKlant != null) || (this.idKlant != null && !this.idKlant.equals(other.idKlant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Klant[ idKlant=" + idKlant + " ]";
    }
    
    @XmlTransient
    public String getNaam(){
        if(tussenvoegsel == null){
            return voornaam + " " + achternaam;
        }
        else {
            return voornaam + " " + tussenvoegsel + " " + achternaam;
        }
    }
    
}
