package dao;

import java.util.List;
import java.util.Map;

public interface RunwayDao {
	public List<Object[]> find10MostCommonRunways();
	public Map<String, List<String>> findDistinctSurfacesPerCountry();
}