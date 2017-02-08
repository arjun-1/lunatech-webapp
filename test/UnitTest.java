import controllers.HomeController;
import controllers.QueryController;

import models.Country;
import models.Airport;
import models.Runway;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;

import play.db.jpa.JPAApi;
import play.mvc.Result;
import play.twirl.api.Content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 *
 * https://www.playframework.com/documentation/latest/JavaTest
 */
public class UnitTest {

    @Test
    public void checkIndex() {
        final HomeController controller = new HomeController();
        final Result result = controller.index();

        assertEquals(OK, result.status());
    }

    @Test
    public void checkQuery() {
        JPAApi jpaApi = mock(JPAApi.class);
        final QueryController controller = new QueryController(jpaApi);
        final Result result = controller.query(null);

        assertEquals(OK, result.status());
    }

    @Test
    public void checkIndexTemplate() {
        Content html = views.html.index.render();
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Hello! What do you want to do?"));
    }

    @Test
    public void checkQueryTemplate() {
        List<Country> countries = new ArrayList<Country>();
        
        Country country = new Country();
        Airport airport = new Airport();
        Runway runway = new Runway();

        runway.le_ident = "mySurface";
        airport.name = "myAirportName";
        country.name = "myCountryName";

        airport.runways = new ArrayList<Runway>();
        airport.runways.add(runway);
        country.airports = new ArrayList<Airport>();
        country.airports.add(airport);
        countries.add(country);
        
        Content html = views.html.query.render(countries);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Country:"));
        assertTrue(contentAsString(html).contains("myCountryName"));
        assertTrue(contentAsString(html).contains("myAirportName"));
        assertTrue(contentAsString(html).contains("mySurface"));
    }

    @Test
    public void checkSubReport1Template() {
        List<Object[]> namesWithCounts = new ArrayList<Object[]>();
        
        Object[] nameCountPair = new Object[2];
        nameCountPair[0] = "myCountryName";
        nameCountPair[1] = 42;
        namesWithCounts.add(nameCountPair);

        Content html = views.html.subReport1.render(namesWithCounts, namesWithCounts);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Countries with the most and least airports"));
        assertTrue(contentAsString(html).contains("myCountryName"));
        assertTrue(contentAsString(html).contains("42"));
    }

    @Test
    public void checkSubReport2Template() {
        Map<String, List<String>> countrySurfacesMap = new TreeMap<String, List<String>>();
        List<String> surfaces = new ArrayList<String>();
        surfaces.add("surface1");
        surfaces.add("surface2");
        countrySurfacesMap.put("myCountryName", surfaces);

        Content html = views.html.subReport2.render(countrySurfacesMap);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Types of runway per country"));
        assertTrue(contentAsString(html).contains("myCountryName"));
        assertTrue(contentAsString(html).contains("surface1"));
        assertTrue(contentAsString(html).contains("surface2"));
    }

    @Test
    public void checkSubReport3Template() {
        List<Object[]> surfacesWithNames = new ArrayList<Object[]>();
        
        Object[] surfaceNamePair = new Object[2];
        surfaceNamePair[0] = "mySurface";
        surfaceNamePair[1] = "myCountryName";
        surfacesWithNames.add(surfaceNamePair);

        Content html = views.html.subReport3.render(surfacesWithNames);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Most common runway identifications"));
        assertTrue(contentAsString(html).contains("mySurface"));
        assertTrue(contentAsString(html).contains("myCountryName"));
        
    }


}
