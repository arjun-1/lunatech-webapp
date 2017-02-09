package controllers;

import models.Country;
import models.Airport;
import models.Runway;

import dao.CountryJPADao;
import dao.RunwayJPADao;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;
// import play.cache.*;
import play.Logger;

import javax.persistence.TypedQuery;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.util.stream.Collectors;


public class ReportController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public ReportController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    // @Cached(key="reportPage")
    @Transactional(readOnly = true)    
    public Result report() {

        CountryJPADao countryDao = new CountryJPADao(jpaApi);
        RunwayJPADao runwayDao = new RunwayJPADao(jpaApi);

        /**
         *  Create list of Object arrays, with each array in the list
         * containing a name and count
         */
        List<Object[]> countriesWithMostAirports = countryDao.find10NamesWithMostAirports();
        List<Object[]> countriesWithLeastAirports = countryDao.find10NamesWithLeastAirports();
        List<Object[]> mostCommonIdents = runwayDao.find10MostCommonIdents();

        /**
         * The following 2 piecs of code are equivalent, but the first is 
         * much slower, since it will implicitly make many SQL queries.
         */

        // SLOW (timed at 4001 ms):
        // long startTime = System.currentTimeMillis();
        // List<Country> allCountries = (List<Country>) jpaApi.em().createNamedQuery("Country.findAll").getResultList();
        // Map<String, List<String>> distinctSurfacesPerCountry = allCountries.stream().collect(
        //     Collectors.toMap(
        //         country -> country.getName(), 
        //         country -> country.getAirports().stream().flatMap(
        //             airport -> airport.getRunways().stream().map(
        //                 runway -> runway.getSurface()
        //             )
        //         ).distinct().collect(Collectors.toList())
        //     )
        // );
        // Long endTime = System.currentTimeMillis();
        // long duration = (endTime - startTime);
        // Logger.debug("This way: it took " + String.valueOf(duration)
        //     + " ms to find the distinct runways per country for all countries.");
        
        // FAST (timed at 498 ms):
        long startTime = System.currentTimeMillis();
       
        Map<String, List<String>> distinctSurfacesPerCountry = runwayDao.findDistinctSurfacesPerCountryName();
        
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        Logger.debug("Other way: it took " + String.valueOf(duration) 
            + " ms to find the distinct runways per country for all countries.");

        // render the report
        Content report1 = views.html.subReport1.render(countriesWithMostAirports, countriesWithLeastAirports);
        Content report2 = views.html.subReport2.render(distinctSurfacesPerCountry);
        Content report3 = views.html.subReport3.render(mostCommonIdents);

        return ok(views.html.report.render(report1, report3, report2));
    }

}
