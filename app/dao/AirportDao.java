package dao;

import models.Airport;
import java.util.List;

public interface AirportDao {
	public List<Airport> findByID(Long id);
	
}