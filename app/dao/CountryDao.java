package dao;

import models.Country;
import java.util.List;

public interface CountryDao  {
	public List<Country> findByISO(String iso);
	public List<Country> findByNameOrISO(String input);
	public List<Object[]> find10CountryNamesWithMostAirports();
	public List<Object[]> findCountryNamesWithLeastAirports();
	public List<String> findAllNames();
}