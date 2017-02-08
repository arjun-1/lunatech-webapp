package dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.*;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;



public class RunwayJPADao implements RunwayDao {

	private final JPAApi jpaApi;

    public RunwayJPADao(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public List<Object[]> find10MostCommonRunways() {
        TypedQuery<Object[]> mostCommonRunwaysQuery = jpaApi.em().createNamedQuery("Runway.le_ident.sortByCountDesc", Object[].class);
        List<Object[]> mostCommonRunways = mostCommonRunwaysQuery.setMaxResults(10).getResultList();
        return mostCommonRunways;
    }
	
    /**
    * Use a list of (unique) surfaces and country names (sorted by country name)
    * to make a map: country name -> List[surfaces]
    * Each element of the list is an object array, of which the first element
    * is assumed to be the surface, and the second the country name
    */
    private static Map<String, List<String>> makeCountrySurfacesMap (List<Object[]> surfacesAndCountries) {

        Map<String, List<String>> countrySurfacesMap = new TreeMap<String, List<String>>();

        if (surfacesAndCountries.size() != 0) {
            /**
            * prevCountry is used to detect 2 subsequent different countries
            * in the rows of surfacesAndCountries
            */
            String prevCountry = null;
            if (surfacesAndCountries.get(0)[1] != null)
                prevCountry = surfacesAndCountries.get(0)[1].toString();
            
            List<String> surfaces = new ArrayList<String>();

            for (Object[] array : surfacesAndCountries) {
                String country = null;
                String surface = null;
                
                if (array[0] != null) surface = array[0].toString();
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
        }
        return countrySurfacesMap;
    }

    /**
    * The list returned by findDistinctSurfacesAndCountries represents a table,
    * of distinct surfaces in the left column, and the country name in the right
    * column, sorted by country name
    */
    @Transactional(readOnly = true)
    private List<Object[]> findDistinctSurfacesAndCountries() {
        TypedQuery<Object[]> surfacesAndCountriesQuery = jpaApi.em().createNamedQuery("Runway.le_ident.distinct.sortByCountry", Object[].class);
        List<Object[]> surfacesAndCountries = surfacesAndCountriesQuery.getResultList();
        return surfacesAndCountries;
    }

    @Transactional(readOnly = true)
    public Map<String, List<String>> findDistinctSurfacesPerCountry() {
        List<Object[]> surfacesAndCountries = this.findDistinctSurfacesAndCountries();
        return makeCountrySurfacesMap(surfacesAndCountries);
    }


}