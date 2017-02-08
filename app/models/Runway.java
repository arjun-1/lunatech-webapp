package models;

import javax.persistence.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;


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

    /**
    * Use a list of (unique) surfaces and country names (sorted by country name)
    * to make a map: country name -> List[surfaces]
    * Each element of the list is an object array, of which the first element
    * is assumed to be the surface, and the second the country name
    */
    public static Map<String, List<String>> makeCountrySurfacesMap (List<Object[]> surfacesAndCountries) {
        Map<String, List<String>> countrySurfacesMap = new TreeMap<String, List<String>>();

        /**
        * a variable to detect 2 subsequent different countries in the
        * rows of surfacesAndCountries
        */
        String prevCountry; 

        if (surfacesAndCountries.size() != 0) prevCountry = surfacesAndCountries.get(0)[1].toString();
        else prevCountry = "";
        List<String> surfaces = new ArrayList<String>();

        for (Object[] array : surfacesAndCountries) {
            String surface = "";
            if (array[0] != null) surface = array[0].toString();
            String country = "";
            if (array[1] != null) country = array[1].toString();
            
            if (country == prevCountry) {
                surfaces.add(surface);
            }
            else {
                countrySurfacesMap.put(prevCountry, surfaces);
                surfaces = new ArrayList<String>();
                prevCountry = country;
            }
        }
        countrySurfacesMap.put(prevCountry, surfaces);
        return countrySurfacesMap;
    }
}
