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

    public Long id;
    
    @Id
    @Column(length = 2)
    public String code;
    
    public String name;
    public String continent;
    public String wikipedia_link;
    public String keywords;

    @JsonIgnore
    @OneToMany(mappedBy = "iso_country", fetch = FetchType.LAZY)
    public List<Airport> airports;

}