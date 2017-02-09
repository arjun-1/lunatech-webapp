package models;

import javax.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQuery(
    name = "Airport.findByID",
   query = "select p from Airport p "
         + "where p.id = :id"
)
@Entity
public class Airport {

    @Id
    private Long id;

    private String ident;
    private String type;
    private String name;
    private String latitude_deg;
    private String longitude_deg;
    private String elevation_ft;
    private String continent;

    @Column(length = 2)
    private String iso_country;
    
    private String iso_region;
    private String municipality;
    private String scheduled_service;
    private String gps_code;
    private String iata_code;
    private String local_code;
    private String home_link;
    private String wikipedia_link;
    private String keywords;

    @JsonIgnore
    @OneToMany(mappedBy = "airport_ref", fetch = FetchType.LAZY)
    private List<Runway> runways;

    // public getID() { return id; }
    public String getName() { return name; }
    // public getISO() { return iso_country; }
    public List<Runway> getRunways() {return runways; }

    /**
     * The following setters are used in the Unit Tests
     */
    public void setName(String name) { this.name = name; }
    public void setRunways(List<Runway> runways) { this.runways = runways; }
}