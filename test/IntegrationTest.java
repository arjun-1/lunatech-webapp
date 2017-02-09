import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static play.test.Helpers.*;

/**
 * Integration testing that involves starting up an application or a server.
 * 
 * https://www.playframework.com/documentation/2.5.x/JavaFunctionalTest
 */
public class IntegrationTest extends WithServer {

    @Test
    public void testQuery() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/query?input=nl");

        Result result = route(app, request);
        final String body = contentAsString(result);
        assertThat(body, containsString("Netherlands"));
        assertThat(body, containsString("Amsterdam Airport Schiphol"));
        assertThat(body, containsString("04"));
        assertThat(body, containsString("06"));
        assertThat(body, containsString("09"));
        assertThat(body, containsString("18C"));
        assertThat(body, containsString("18L"));
        assertThat(body, containsString("18R"));
    }

    @Test
    public void testCountryNames() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/countries/name");

        Result result = route(app, request);
        final String body = contentAsString(result);
        assertThat(body, containsString("Netherlands"));
        assertThat(body, containsString("Andorra"));
        assertThat(body, containsString("Zimbabwe"));
    }

    @Test
    public void testReport() throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/report");

        Result result = route(app, request);
        final String body = contentAsString(result);
        assertThat(body, containsString("United States"));
        assertThat(body, containsString("Brazil"));
        assertThat(body, containsString("Canada"));
        assertThat(body, containsString("21501"));
        assertThat(body, containsString("H1"));
        assertThat(body, containsString("5566"));
        assertThat(body, containsString("CONCRETE AND ASP"));
        assertThat(body, containsString("South Georgia and the South Sandwich Islands"));
    }


}
