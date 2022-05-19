package ru.nsu.kosarev.db.organizer;

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
import ru.nsu.kosarev.db.organizer.dto.PeriodDTO;
import ru.nsu.kosarev.db.organizer.projection.OrganizerEventProjection;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;

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
public class OrganizerSpecificIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getOrganizersWithEventCountsInPeriod() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/organizer/getOrganizersWithEventCountsInPeriod/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PeriodDTO> request = new HttpEntity<>(
            new PeriodDTO(
                Date.valueOf("2000-10-30"),
                Date.valueOf("2100-02-15")
            ),
            headers
        );

        List<OrganizerWithEventCountProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<OrganizerWithEventCountProjection>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Andrey", "Vasya");
        List<String> surnames = Arrays.asList("Nikolotov", "Pupkin");
        List<String> birthDates = Arrays.asList("31/12/2000", "01/01/2000");
        List<Integer> eventCounts = Arrays.asList(1, 2);

        assertEquals(2, projections.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), projections.get(i).getOrganizerName());
            assertEquals(surnames.get(i), projections.get(i).getOrganizerSurname());
            assertEquals(birthDates.get(i), projections.get(i).getOrganizerBirthDate());
            assertEquals(eventCounts.get(i), projections.get(i).getEventCount());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getEventsOfAllOrganizers() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/organizer/allOrganizersEvents/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        List<OrganizerEventProjection> projections = testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<List<OrganizerEventProjection>>() {}
        ).getBody();

        List<String> organizerNames = Arrays.asList("Nikita", "Marat", "Andrey", "Vasya", "Vasya");
        List<String> organizerSurnames = Arrays.asList("Kosarev", "Pashentsev", "Nikolotov", "Pupkin", "Pupkin");
        List<String> organizerBirthDates = Arrays.asList("30/10/2000", "24/05/2000", "31/12/2000", "01/01/2000", "01/01/2000");
        List<String> eventNames = Arrays.asList("Musicle of Pushkin", "Triller of Kolotushkin", "Concert of Pushkin",
            "Concert of Pushkin", "Opera of Kolotushkin");
        List<String> eventTypes = Arrays.asList("FILM", "FILM", "CONCERT", "CONCERT", "PERFORMANCE");
        List<String> eventPlaces = Arrays.asList("Kakoi-to kinoteatr", "Kakoi-to kinoteatr", "Kakoi-to teatr",
            "Kakoi-to teatr", "Kakoi-to teatr");
        List<String> eventDates = Arrays.asList("01/03/2100", "01/04/2100", "01/01/2100", "01/01/2100", "01/02/2100");

        assertEquals(5, projections.size());

        for (int i = 0; i < 5; ++i) {
            assertEquals(organizerNames.get(i), projections.get(i).getOrganizerName());
            assertEquals(organizerSurnames.get(i), projections.get(i).getOrganizerSurname());
            assertEquals(organizerBirthDates.get(i), projections.get(i).getOrganizerBirthDate());
            assertEquals(eventNames.get(i), projections.get(i).getEventName());
            assertEquals(eventTypes.get(i), projections.get(i).getEventType());
            assertEquals(eventPlaces.get(i), projections.get(i).getEventPlace());
            assertEquals(eventDates.get(i), projections.get(i).getEventDate());
        }
    }

}
