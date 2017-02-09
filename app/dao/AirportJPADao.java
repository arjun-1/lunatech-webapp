package dao;

import models.Airport;

import java.util.List;
import javax.persistence.*;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;



public class AirportJPADao implements AirportDao {

	private final JPAApi jpaApi;

    public AirportJPADao(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }


    public List<Airport> findByID(Long id) {
        TypedQuery<Airport> airportsQuery = jpaApi.em().createNamedQuery("Airport.findByID", Airport.class).setParameter("id", id);
        List<Airport> airports = airportsQuery.getResultList();
        return airports;
    }

}