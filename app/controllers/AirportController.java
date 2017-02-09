package controllers;

import models.Country;
import models.Airport;
import dao.CountryJPADao;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.TypedQuery;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class AirportController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public AirportController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getAirportsByCountryISO(String iso) {

        CountryJPADao countryDao = new CountryJPADao(jpaApi);
        List<Country> countries = countryDao.findByISO(iso);

        List<Airport> airports = countries.stream().flatMap(
            country -> country.getAirports().stream()
        ).collect(Collectors.toList());
        return ok(toJson(airports));
    }



}
