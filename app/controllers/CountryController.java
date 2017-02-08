package controllers;

import models.Country;
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

public class CountryController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public CountryController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getCountryNames() {

        CountryJPADao countryDao = new CountryJPADao(jpaApi);
        List<String> countryNames = countryDao.findAllNames();
        return ok(toJson(countryNames));
    }

}
