package dao;

import models.Country;

import java.util.List;
import javax.persistence.*;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;



public class CountryJPADao implements CountryDao {

	private final JPAApi jpaApi;

    public CountryJPADao(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<Country> findByISO(String iso) {
        TypedQuery<Country> countriesQuery = jpaApi.em().createNamedQuery("Country.findByISO", Country.class).setParameter("iso", iso);
        List<Country> countries = countriesQuery.getResultList();
        return countries;
    }
	
	public List<Country> findByNameOrISO(String input) {
		TypedQuery<Country> countriesQuery = jpaApi.em().createNamedQuery("Country.findByNameOrISO", Country.class).setParameter("input", input);
        List<Country> countries = countriesQuery.getResultList();
        return countries;
	}

	public List<Object[]> find10NamesWithMostAirports() {
		List<Object[]> countriesWithMostAirports = jpaApi.em().createNamedQuery("Country.name.sortByAirportCountDesc").setMaxResults(10).getResultList();
        return countriesWithMostAirports;
    }

    public List<Object[]> find10NamesWithLeastAirports() {
        List<Object[]> countriesWithMostAirports = jpaApi.em().createNamedQuery("Country.name.sortByAirportCountAsc").setMaxResults(10).getResultList();
        return countriesWithMostAirports;
    }



    public List<String> findAllNames() {
    	TypedQuery<String> countryNamesQuery = jpaApi.em().createNamedQuery("Country.name.findAll", String.class);
        List<String> countryNames = countryNamesQuery.getResultList();
        return countryNames;
    }

}