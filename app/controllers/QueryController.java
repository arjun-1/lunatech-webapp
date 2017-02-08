package controllers;

import models.Country;

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
        List<Country> countries;
        if (input != null) {
            TypedQuery<Country> countryQuery = jpaApi.em().createNamedQuery("Country.findByNameOrISO", Country.class).setParameter("input", input);
            countries = countryQuery.getResultList();
        }
        else {
            countries = new ArrayList<Country>();   
        }
        return ok(views.html.query.render(countries));
    }


}
