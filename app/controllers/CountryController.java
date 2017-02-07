package controllers;

import models.Country;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class CountryController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public CountryController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getCountryNames() {
        List<String> countryNames  = (List<String>) jpaApi.em().createNamedQuery("Country.name.findAll").getResultList();
        return ok(toJson(countryNames));
    }

}
