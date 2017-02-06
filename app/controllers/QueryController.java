package controllers;

import models.Country;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class QueryController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public QueryController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result query(String country) {
        String iso = country; // TODO: make fuzzy
        List<Country> countries  = (List<Country>) jpaApi.em().createNamedQuery("Country.findByISO").setParameter("iso", iso).getResultList();
        return ok(views.html.query.render(countries));
    }


}
