package ru.nsu.kosarev.db.event;

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
import ru.nsu.kosarev.db.event.dto.PeriodOrganizerDTO;
import ru.nsu.kosarev.db.event.projection.ArtistWithPlaceProjection;
import ru.nsu.kosarev.db.event.projection.EventProjection;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class EventSpecificIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getEventsInPeriodOrByOrganizer() throws URISyntaxException {
        int organizerId = 1;

        final String baseUrl = "http://localhost:" + port + "/event/getEventsInPeriodOrByOrganizer/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PeriodOrganizerDTO> request = new HttpEntity<>(
            new PeriodOrganizerDTO(
                Date.valueOf("2000-10-30"),
                Date.valueOf("2100-02-15"),
                organizerId
            ),
            headers
        );

        List<EventProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<EventProjection>>() {
                }
            ).getBody();

        List<String> eventNames = Arrays.asList("Concert of Pushkin", "Opera of Kolotushkin", "Musicle of Pushkin");
        List<String> eventTypes = Arrays.asList("CONCERT", "PERFORMANCE", "FILM");
        List<String> buildingNames = Arrays.asList("Kakoi-to teatr", "Kakoi-to teatr", "Kakoi-to kinoteatr");
        List<String> eventDates = Arrays.asList("01/01/2100", "01/02/2100", "01/03/2100");

        assertEquals(3, projections.size());
        for (int i = 0; i < 3; ++i) {
            assertEquals(eventNames.get(i), projections.get(i).getName());
            assertEquals(eventTypes.get(i), projections.get(i).getEventType());
            assertEquals(buildingNames.get(i), projections.get(i).getEventPlace());
            assertEquals(eventDates.get(i), projections.get(i).getEventDate());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArtistsWithPlaces() throws URISyntaxException {
        int eventId = 3;

        final String baseUrl = "http://localhost:" + port + "/event/getArtistsWithPlaces/" + eventId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(
            null,
            headers
        );

        List<ArtistWithPlaceProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ArtistWithPlaceProjection>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Nikita", "Marat", "Andrey");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev", "Nikolotov");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000", "31/12/2000");
        List<Integer> places = Arrays.asList(1, 2, 3);

        assertEquals(3, projections.size());
        for (int i = 0; i < 3; ++i) {
            assertEquals(names.get(i), projections.get(i).getArtistName());
            assertEquals(surnames.get(i), projections.get(i).getArtistSurname());
            assertEquals(birthDates.get(i), projections.get(i).getArtistBirthDate());
            assertEquals(places.get(i), projections.get(i).getArtistPlace());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getEventsInBuilding() throws URISyntaxException {
        int buildingId = 1;

        final String baseUrl = "http://localhost:" + port + "/event/getEventsInBuilding/" + buildingId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(
            null,
            headers
        );

        List<EventProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<EventProjection>>() {
                }
            ).getBody();

        List<String> eventNames = Arrays.asList("Concert of Pushkin", "Opera of Kolotushkin");
        List<String> eventTypes = Arrays.asList("CONCERT", "PERFORMANCE");
        List<String> buildingNames = Arrays.asList("Kakoi-to teatr", "Kakoi-to teatr");
        List<String> eventDates = Arrays.asList("01/01/2100", "01/02/2100");

        assertEquals(2, projections.size());
        for (int i = 0; i < 2; ++i) {
            assertEquals(eventNames.get(i), projections.get(i).getName());
            assertEquals(eventTypes.get(i), projections.get(i).getEventType());
            assertEquals(buildingNames.get(i), projections.get(i).getEventPlace());
            assertEquals(eventDates.get(i), projections.get(i).getEventDate());
        }
    }

}
