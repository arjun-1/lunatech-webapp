package controllers;

import models.Country;
import models.Airport;
import models.Runway;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.toJson;
import play.twirl.api.Content;
import play.cache.*;

import java.util.stream.Collectors;
import java.util.Map;
import play.Logger;

public class ReportController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public ReportController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Cached(key="reportPage")
    @Transactional(readOnly = true)
    public Result report() {
        List<Object[]> countriesWithMostAirports = (List<Object[]>) jpaApi.em().createNamedQuery("Country.name.sortByAirportCountDesc").setMaxResults(10).getResultList();
        List<Object[]> countriesWithLeastAirports = (List<Object[]>) jpaApi.em().createNamedQuery("Country.name.havingLeastAirportCount").getResultList();
        List<Object[]> mostCommonRunway = (List<Object[]>) jpaApi.em().createNamedQuery("Runway.le_ident.sortByCountDesc").setMaxResults(10).getResultList();
        List<Country> allCountries = (List<Country>) jpaApi.em().createNamedQuery("Country.findAll").getResultList();

        Long startTime = System.currentTimeMillis();
        Map<String, List<String>> distinctRunwaySurfacesPerCountry = allCountries.stream().collect(
            Collectors.toMap(
                country -> country.name, 
                country -> country.airports.stream().flatMap(
                    airport -> airport.runways.stream().map(
                        runway -> runway.surface
                    )
                ).distinct().collect(Collectors.toList())
            )
        );
        Long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        Logger.debug(String.valueOf(duration));


        // List<Object[]> blaat = (List<Object[]>) jpaApi.em().createQuery(
        //     "select distinct(surface), Country.name " 
        //   + "from (Airport inner join Runway on Airport.id = Runway.airport_ref) inner join Country on Airport.iso_country = Country.code "
        //   + "order by iso_country").getResultList();
   
        Content report1 = views.html.subReport1.render(countriesWithMostAirports, countriesWithLeastAirports);
        Content report2 = views.html.subReport2.render(distinctRunwaySurfacesPerCountry);
        Content report3 = views.html.subReport3.render(mostCommonRunway);

        return ok(views.html.report.render(report1, report3, report2));
        // return ok(views.html.subReport2.render(distinctRunwaySurfacesPerCountry));
    }

    // Maak hier 1 Result van


}
