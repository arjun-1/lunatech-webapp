package controllers;

import models.Country;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import play.mvc.Controller;
import play.mvc.Result;



public class HomeController extends Controller {


    public Result index() {
        return ok(views.html.index.render());
    }


}
