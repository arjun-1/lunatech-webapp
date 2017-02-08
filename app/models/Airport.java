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
    public Long id;

    public String ident;
    public String type;
    public String name;
    public String latitude_deg;
    public String longitude_deg;
    public String elevation_ft;
    public String continent;

    @Column(length = 2)
    public String iso_country;
    
    public String iso_region;
    public String municipality;
    public String scheduled_service;
    public String gps_code;
    public String iata_code;
    public String local_code;
    public String home_link;
    public String wikipedia_link;
    public String keywords;

    @JsonIgnore
    @OneToMany(mappedBy = "airport_ref", fetch = FetchType.LAZY)
    public List<Runway> runways;

}