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
       query = "select c.name, count(*) as countryCount "
             + "from Airport a, Country c "
             + "where c.code = a.iso_country "
             + "group by a.iso_country "
             + "order by countryCount desc "
    )
})
// JPA does not allow subqueries in the from clause, so we resort to native query
@NamedNativeQuery(
    name = "Country.name.havingLeastAirportCount",
   query = "select Country.name, count(*) "
         + "from Airport inner join Country on Country.code = Airport.iso_country "
         + "group by iso_country "
         + "having count(*) = (select min(countryCount) from (select count(*) as countryCount from Airport group by iso_country) M)"
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
    @OneToMany(mappedBy="iso_country", fetch=FetchType.LAZY)
    public List<Airport> airports;

}