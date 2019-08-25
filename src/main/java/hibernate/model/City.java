package hibernate.model;

import javax.persistence.*;

@Entity(name = "city")
@NamedQueries({
    @NamedQuery(name = "citiesByName", query = "FROM city c WHERE c.name = :cityNameParam"),
    @NamedQuery(name = "citiesByCountryCode", query = "FROM city c WHERE c.country.code = :countryCodeParam")
})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String district;
    private Long population;

    @ManyToOne
    @JoinColumn(name = "countryCode")
    private Country country;

    public City() {
    }

    public City(String name, String district, Long population, Country country) {
        this.name = name;
        this.district = district;
        this.population = population;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
