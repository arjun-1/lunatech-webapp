package models;

import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
    name = "Runway.le_ident.sortByCountDesc",
   query = "select r.le_ident, count(r.le_ident)"
         + "from Runway r "
         + "group by r.le_ident "
         + "order by count(r.le_ident) desc"
    ),
    @NamedQuery(
    name = "Runway.le_ident.distinct.sortByCountry",
   query = "select distinct(r.surface), c.name " 
          + "from Airport a, Runway r, Country c "
          + "where a.id = r.airport_ref and c.code = a.iso_country "
          + "order by c.name "
    )
})
@Entity
public class Runway {
    
    @Id
    private Long id;

    private Long airport_ref;
    private String airport_ident;
    private String length_ft;
    private String width_ft;
    private String surface;
    private String lighted;
    private String closed;
    private String le_ident;
    private String le_latitude_deg;
    private String le_longitude_deg;
    private String le_elevation_ft;
    private String le_heading_degT;
    private String le_displaced_threshold_ft;
    private String he_ident;
    private String he_latitude_deg;
    private String he_longitude_deg;
    private String he_elevation_ft;
    private String he_heading_degT;
    private String he_displaced_threshold_ft;

    // public getID() { return id; }
    public String getIdent() { return le_ident; }
    public String getSurface() { return surface; }

    /**
     * The following setters are used in the Unit Tests
     */
    public void setIdent(String le_ident) { this.le_ident = le_ident; }
    
}
