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
import java.util.ArrayList;

public class QueryController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public QueryController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result query(String input) {
        List<Country> countries = new ArrayList<Country>();
        if (input != null) {
            CountryJPADao countryDao = new CountryJPADao(jpaApi);
            countries = countryDao.findByNameOrISO(input);
        }
        return ok(views.html.query.render(countries));
    }


}
