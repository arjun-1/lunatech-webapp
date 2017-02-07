package models;

import javax.persistence.*;

@NamedQuery(
    name = "Runway.le_ident.sortByCountDesc",
   query = "select r.le_ident, count(*)"
         + "from Runway r "
         + "group by r.le_ident "
         + "order by count(*) desc"
)
@Entity
public class Runway {
    
    @Id
    public Long id;

    public Long airport_ref;
    public String airport_ident;
    public String length_ft;
    public String width_ft;
    public String surface;
    public String lighted;
    public String closed;
    public String le_ident;
    public String le_latitude_deg;
    public String le_longitude_deg;
    public String le_elevation_ft;
    public String le_heading_degT;
    public String le_displaced_threshold_ft;
    public String he_ident;
    public String he_latitude_deg;
    public String he_longitude_deg;
    public String he_elevation_ft;
    public String he_heading_degT;
    public String he_displaced_threshold_ft;

}
