package controllers;

import models.Runway;
import models.Airport;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class RunwayController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public RunwayController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getRunwaysByAirportID(Long id) {
        List<Airport> airports  = (List<Airport>) jpaApi.em().createNamedQuery("Airport.findByID").setParameter("id", id).getResultList();
        List<Runway> runways = airports.stream().flatMap(
            airport -> airport.runways.stream()
        ).collect(Collectors.toList());

        return ok(toJson(runways));
    }

}
