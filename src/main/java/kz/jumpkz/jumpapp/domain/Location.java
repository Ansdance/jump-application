package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @JsonIgnoreProperties(value = { "region", "location" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Country country;

    @JsonIgnoreProperties(value = { "location", "players", "statisticHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Tournament tournament;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Location postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public Location city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Location stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        if (this.tournament != null) {
            this.tournament.setLocation(null);
        }
        if (tournament != null) {
            tournament.setLocation(this);
        }
        this.tournament = tournament;
    }

    public Location tournament(Tournament tournament) {
        this.setTournament(tournament);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            "}";
    }
}
