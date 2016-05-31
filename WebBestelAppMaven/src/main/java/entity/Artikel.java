/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ian
 */
@Entity
@Table(name = "artikel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a"),
    @NamedQuery(name = "Artikel.findByIdArtikel", query = "SELECT a FROM Artikel a WHERE a.idArtikel = :idArtikel"),
    @NamedQuery(name = "Artikel.findByArtikelnaam", query = "SELECT a FROM Artikel a WHERE a.artikelnaam = :artikelnaam"),
    @NamedQuery(name = "Artikel.findByArtikelprijs", query = "SELECT a FROM Artikel a WHERE a.artikelprijs = :artikelprijs"),
    @NamedQuery(name = "Artikel.findByArtikelnummer", query = "SELECT a FROM Artikel a WHERE a.artikelnummer = :artikelnummer"),
    @NamedQuery(name = "Artikel.findByArtikelomschrijving", query = "SELECT a FROM Artikel a WHERE a.artikelomschrijving = :artikelomschrijving")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArtikel")
    private Integer idArtikel;
    @Basic(optional = false)
    @NotNull(message="Een naam moet ingevuld worden")
    @Size(min = 1, max = 80, message="Het artikelnaam mag maximaal 80 karakters lang zijn")
    @Column(name = "artikelnaam")
    private String artikelnaam;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull(message="Het artikel moet een prijs hebben")
    @Column(name = "artikelprijs")
    @Digits(integer=4, fraction=2, message="De prijs mag maximaal 4 cijfers voor de komma, en 2 cijfers na de komma hebben")
    private BigDecimal artikelprijs;
    @Basic(optional = false)
    @NotNull(message="Het artikel moet een nummer hebben")
    @Size(min = 1, max = 45, message="Het artikelnummer mag maximaal 45 karakters lang zijn")
    @Column(name = "artikelnummer")
    private String artikelnummer;
    @Size(max = 80, message="Omschrijving mag maximaal 80 karakters lang zijn")
    @Column(name = "artikelomschrijving")
    private String artikelomschrijving;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikelidArtikel")
    private Collection<BestellingHasArtikel> bestellingHasArtikelCollection;

    public Artikel() {
    }

    public Artikel(Integer idArtikel) {
        this.idArtikel = idArtikel;
    }

    public Artikel(Integer idArtikel, String artikelnaam, BigDecimal artikelprijs, String artikelnummer) {
        this.idArtikel = idArtikel;
        this.artikelnaam = artikelnaam;
        this.artikelprijs = artikelprijs;
        this.artikelnummer = artikelnummer;
    }

    public Integer getIdArtikel() {
        return idArtikel;
    }

    public void setIdArtikel(Integer idArtikel) {
        this.idArtikel = idArtikel;
    }

    public String getArtikelnaam() {
        return artikelnaam;
    }

    public void setArtikelnaam(String artikelnaam) {
        this.artikelnaam = artikelnaam;
    }

    public BigDecimal getArtikelprijs() {
        return artikelprijs;
    }

    public void setArtikelprijs(BigDecimal artikelprijs) {
        this.artikelprijs = artikelprijs;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public void setArtikelnummer(String artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public String getArtikelomschrijving() {
        return artikelomschrijving;
    }

    public void setArtikelomschrijving(String artikelomschrijving) {
        this.artikelomschrijving = artikelomschrijving;
    }

    @XmlTransient
    public Collection<BestellingHasArtikel> getBestellingHasArtikelCollection() {
        return bestellingHasArtikelCollection;
    }

    public void setBestellingHasArtikelCollection(Collection<BestellingHasArtikel> bestellingHasArtikelCollection) {
        this.bestellingHasArtikelCollection = bestellingHasArtikelCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArtikel != null ? idArtikel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.idArtikel == null && other.idArtikel != null) || (this.idArtikel != null && !this.idArtikel.equals(other.idArtikel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Artikel[ idArtikel=" + idArtikel + " ]";
    }
    
}
