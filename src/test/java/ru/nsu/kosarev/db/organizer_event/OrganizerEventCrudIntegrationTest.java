package ru.nsu.kosarev.db.organizer_event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import ru.nsu.kosarev.db.DbApplication;
import ru.nsu.kosarev.db.organizer.projection.OrganizerEventProjection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class OrganizerEventCrudIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("init_organizer_event.sql")
    @Sql(scripts = "clear_organizer_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchAll() throws URISyntaxException {
        List<OrganizerEventProjection> projections = fetchCurrentRows();

        assertEquals(3, projections.size());

        List<String> organizerNames = Arrays.asList("Nikita", "Nikita", "Nikita");
        List<String> organizerSurnames = Arrays.asList("Kosarev", "Kosarev", "Kosarev");
        List<String> organizerBirthDates = Arrays.asList("30/10/2000", "30/10/2000", "30/10/2000");
        List<String> eventNames = Arrays.asList("Concert of Pushkin", "Opera of Kolotushkin", "Musicle of Pushkin");
        List<String> eventTypes = Arrays.asList("CONCERT", "PERFORMANCE", "FILM");
        List<String> eventPlaces = Arrays.asList("Kakoi-to teatr", "Kakoi-to teatr", "Kakoi-to kinoteatr");
        List<String> eventDates = Arrays.asList("01/01/2100", "01/02/2100", "01/03/2100");

        for (int i = 0; i < 3; ++i) {
            assertEquals(organizerNames.get(i), projections.get(i).getOrganizerName());
            assertEquals(organizerSurnames.get(i), projections.get(i).getOrganizerSurname());
            assertEquals(organizerBirthDates.get(i), projections.get(i).getOrganizerBirthDate());
            assertEquals(eventNames.get(i), projections.get(i).getEventName());
            assertEquals(eventTypes.get(i), projections.get(i).getEventType());
            assertEquals(eventPlaces.get(i), projections.get(i).getEventPlace());
            assertEquals(eventDates.get(i), projections.get(i).getEventDate());
        }
    }

    @Test
    @Sql("init_organizer_event.sql")
    @Sql(scripts = "clear_organizer_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void create() throws URISyntaxException {
        assertEquals(3, fetchCurrentRows().size());

        int organizerId = 1;
        int eventId = 1;

        final String baseUrl = "http://localhost:" + port + "/organizer/organizerEvent/create/"
            + organizerId + "/" + eventId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<>() {}
        );

        assertEquals(4, fetchCurrentRows().size());
    }

    @Test
    @Sql("init_organizer_event.sql")
    @Sql(scripts = "clear_organizer_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete() throws URISyntaxException {
        assertEquals(3, fetchCurrentRows().size());

        int organizerId = 1;
        int eventId = 1;

        final String baseUrl = "http://localhost:" + port + "/organizer/organizerEvent/delete/"
            + organizerId + "/" + eventId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<>() {}
        );

        assertEquals(2, fetchCurrentRows().size());
    }

    private List<OrganizerEventProjection> fetchCurrentRows() throws URISyntaxException {
        int organizerId = 1;

        final String baseUrl = "http://localhost:" + port + "/organizer/organizerEvent/get/" + organizerId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        return testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<List<OrganizerEventProjection>>() {}
        ).getBody();
    }

}
