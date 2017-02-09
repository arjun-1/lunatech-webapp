package models;

import javax.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.libs.Json;

@NamedQueries({
    @NamedQuery(
        name = "Country.findAll",
       query = "select c " 
             + "from Country c "
    ),
    @NamedQuery(
        name = "Country.findByISO",
       query = "select c " 
             + "from Country c "
             + "where c.code = upper(:iso) "
    ),
    @NamedQuery(
        name = "Country.findByName",
       query = "select c " 
             + "from Country c "
             + "where lower(c.name) = lower(:name) "
    ),
    @NamedQuery(
        name = "Country.findByNameOrISO",
       query = "select c " 
             + "from Country c "
             + "where lower(c.name) = lower(:input) or c.code = upper(:input) "
    ),
    @NamedQuery(
        name = "Country.name.findAll",
       query = "select c.name " 
             + "from Country c "
    ),
    @NamedQuery(
        name = "Country.name.sortByAirportCountDesc", 
       query = "select c.name, count(a.name) as airportCount "
             + "from Airport a, Country c where c.code = a.iso_country "
             + "group by a.iso_country "
             + "order by airportCount desc "
    )
})
// JPA does not allow subqueries in the from clause, so we resort to native query
@NamedNativeQuery(
    name = "Country.name.havingLeastAirportCount",
   query = "select c.name, count(a.name) as airportCount "
         + "from Airport a, Country c where c.code = a.iso_country "
         + "group by a.iso_country "
         + "having count(a.name) = (select min(airportCount) from (select count(a.name) as airportCount from Airport a group by a.iso_country) Least) "
)
@Entity
public class Country {

    private Long id;
    
    @Id
    @Column(length = 2)
    private String code;
    
    private String name;
    private String continent;
    private String wikipedia_link;
    private String keywords;

    @JsonIgnore
    @OneToMany(mappedBy = "iso_country", fetch = FetchType.LAZY)
    private List<Airport> airports;

    // public String getISO() { return code; }
    public String getName() { return name; }
    public List<Airport> getAirports() { return airports; }

    /**
     * The following setters are used in the Unit Tests
     */
    public void setName(String name) { this.name = name; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }


}