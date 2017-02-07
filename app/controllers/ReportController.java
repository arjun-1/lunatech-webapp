package controllers;

import models.Country;
import models.Airport;
import models.Runway;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.TreeMap;

import play.twirl.api.Content;
// import play.cache.*;


import play.Logger;

public class ReportController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public ReportController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    // @Cached(key="reportPage")
    @Transactional(readOnly = true)
    public Result report() {

        // Create list of String arrays, with each array in the list
        // containing a name and count
        TypedQuery<Object[]> countriesWithMostAirportsQuery = jpaApi.em().createNamedQuery("Country.name.sortByAirportCountDesc", Object[].class);
        List<Object[]> countriesWithMostAirports = countriesWithMostAirportsQuery.setMaxResults(10).getResultList();

        TypedQuery<Object[]> mostCommonRunwaysQuery = jpaApi.em().createNamedQuery("Runway.le_ident.sortByCountDesc", Object[].class);
        List<Object[]> mostCommonRunways = mostCommonRunwaysQuery.setMaxResults(10).getResultList();

        // TypedQuery doesn't work for a NativeQuery...
        List<Object[]> countriesWithLeastAirports = jpaApi.em().createNamedQuery("Country.name.havingLeastAirportCount").getResultList();

        // The following 2 piecs of code are equivalent, but the first is 
        // much slower, since it will implicitly make many SQL queries.
        //
        // SLOW (timed at 4001 ms):
        
        // Long startTime = System.currentTimeMillis();
        // List<Country> allCountries = (List<Country>) jpaApi.em().createNamedQuery("Country.findAll").getResultList();
        // Map<String, List<String>> distinctRunwaySurfacesPerCountry = allCountries.stream().collect(
        //     Collectors.toMap(
        //         country -> country.name, 
        //         country -> country.airports.stream().flatMap(
        //             airport -> airport.runways.stream().map(
        //                 runway -> runway.surface
        //             )
        //         ).distinct().collect(Collectors.toList())
        //     )
        // );
        // Long endTime = System.currentTimeMillis();
        // long duration = (endTime - startTime);
        // Logger.debug("This way: it took " + String.valueOf(duration)
        //     + " ms to find the distinct runways per country for all countries.");
        
        // FAST (timed at 498 ms):
        Long startTime = System.currentTimeMillis();

        // The following represents a table, of distinct surfaces in the left
        // column, and the country name in the right column, sorted by country
        // name

        TypedQuery<Object[]> surfacesAndCountriesQuery = jpaApi.em().createQuery(
            "select distinct(r.surface), c.name " 
          + "from Airport a, Runway r, Country c "
          + "where a.id = r.airport_ref and c.code = a.iso_country "
          + "order by c.name", Object[].class);
        List<Object[]> surfacesAndCountries = surfacesAndCountriesQuery.getResultList();

        // We will use the list of surfaces and countries
        // to make a map: country -> List[surfaces]
        Map <String, List<String>> distinctRunwaySurfacesPerCountry = new TreeMap<String, List<String>>();
        
        // a variable to detect 2 subsequent different countries in the
        // rows of surfacesAndCountries
        String prev_country; 

        if (surfacesAndCountries.size() != 0) prev_country = surfacesAndCountries.get(0)[1].toString();
        else prev_country = "";
        List<String> surfaces = new ArrayList<String>();

        for (Object[] array : surfacesAndCountries) {
            String surface = "";
            if (array[0] != null) surface = array[0].toString();
            String country = array[1].toString();
            
            if (country == prev_country) {
                surfaces.add(surface);
            } else {
                distinctRunwaySurfacesPerCountry.put(prev_country, surfaces);
                surfaces = new ArrayList<String>();
                prev_country = country;
            }
        }
        distinctRunwaySurfacesPerCountry.put(prev_country, surfaces);
        Long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        Logger.debug("Other way: it took " + String.valueOf(duration) 
            + " ms to find the distinct runways per country for all countries.");

        // render the report
        Content report1 = views.html.subReport1.render(countriesWithMostAirports, countriesWithLeastAirports);
        Content report2 = views.html.subReport2.render(distinctRunwaySurfacesPerCountry);
        Content report3 = views.html.subReport3.render(mostCommonRunways);

        return ok(views.html.report.render(report1, report3, report2));
    }

}
