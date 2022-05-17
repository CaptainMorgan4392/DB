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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import ru.nsu.kosarev.db.DbApplication;
import ru.nsu.kosarev.db.event.dto.EventDTO;
import ru.nsu.kosarev.db.event.dto.EventResponseDTO;
import ru.nsu.kosarev.db.event.repository.EventRepository;
import ru.nsu.kosarev.db.event.sortingfilter.EventSearchParams;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class EventCrudIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Sql("init_event.sql")
    @Sql(scripts = "clear_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchAll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/event/fetch/list";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EventSearchParams> request = new HttpEntity<>(new EventSearchParams(), headers);

        ResponseEntity<List<EventResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<EventResponseDTO>>() {}
            );

        List<EventResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(eventResponseDTO ->
            eventResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList(
            "Concert of Pushkin",
            "Opera of Kolotushkin",
            "Musicle of Pushkin",
            "Triller of Kolotushkin"
        );

        List<String> eventTypes = Arrays.asList(
            "CONCERT",
            "PERFORMANCE",
            "FILM",
            "FILM"
        );

        List<String> eventPlaces = Arrays.asList(
            "Kakoi-to teatr",
            "Kakoi-to teatr",
            "Kakoi-to kinoteatr",
            "Kakoi-to kinoteatr"
        );
        List<String> eventDates = Arrays.asList("01/01/2100", "01/01/2100", "01/01/2100", "01/01/2100");

        assertEquals(4, dtosWithoutIds.size());

        for (int i = 0; i < 4; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(eventTypes.get(i), dtosWithoutIds.get(i).getEventType());
            assertEquals(eventPlaces.get(i), dtosWithoutIds.get(i).getEventPlace());
            assertEquals(eventDates.get(i), dtosWithoutIds.get(i).getEventDate());
        }
    }

    @Test
    @Sql("init_event.sql")
    @Sql(scripts = "clear_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchPage() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/event/fetch/page";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EventSearchParams> request = new HttpEntity<>(
            new EventSearchParams(
                null,
                null,
                null,
                null,
                null,
                0,
                2
            ),
            headers
        );

        ResponseEntity<List<EventResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<EventResponseDTO>>() {}
            );

        List<EventResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(eventResponseDTO ->
            eventResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList(
            "Concert of Pushkin",
            "Opera of Kolotushkin"
        );

        List<String> eventTypes = Arrays.asList(
            "CONCERT",
            "PERFORMANCE"
        );

        List<String> eventPlaces = Arrays.asList(
            "Kakoi-to teatr",
            "Kakoi-to teatr"
        );
        List<String> eventDates = Arrays.asList("01/01/2100", "01/01/2100");

        assertEquals(2, dtosWithoutIds.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(eventTypes.get(i), dtosWithoutIds.get(i).getEventType());
            assertEquals(eventPlaces.get(i), dtosWithoutIds.get(i).getEventPlace());
            assertEquals(eventDates.get(i), dtosWithoutIds.get(i).getEventDate());
        }
    }

    @Test
    @Sql("init_event.sql")
    @Sql(scripts = "clear_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void create() throws URISyntaxException {
        assertEquals(4, eventRepository.findAll().size());

        final String baseUrl = "http://localhost:" + port + "/event/add";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EventDTO> request = new HttpEntity<>(
            new EventDTO(
                null,
                "Marat pukaet",
                2,
                1,
                Date.valueOf("2100-01-01")
            ),
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            EventResponseDTO.class
        );

        assertEquals(5, eventRepository.findAll().size());
    }

    @Test
    @Sql("init_event.sql")
    @Sql(scripts = "clear_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void update() throws URISyntaxException {
        List<Event> found = eventRepository.findAll();
        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/event/update";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EventDTO toUpdate = new EventDTO(
            fetchedId,
            "Marat pukaet",
            2,
            1,
            Date.valueOf("2100-01-01")
        );

        HttpEntity<EventDTO> request = new HttpEntity<>(
            toUpdate,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            EventResponseDTO.class
        );

        Event event = eventRepository.findById(fetchedId).get();

        assertEquals(toUpdate.getId(), event.getId());
        assertEquals(toUpdate.getName(), event.getName());
        assertEquals(toUpdate.getEventType(), event.getEventType());
        assertEquals(toUpdate.getEventPlace(), event.getEventPlace());
        assertEquals(toUpdate.getEventDate(), event.getEventDate());
    }

    @Test
    @Sql("init_event.sql")
    @Sql(scripts = "clear_event.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete() throws URISyntaxException {
        List<Event> found = eventRepository.findAll();
        assertEquals(4, found.size());
        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/event/delete/" + fetchedId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EventDTO> request = new HttpEntity<>(
            null,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            Object.class
        );

        assertEquals(3, eventRepository.findAll().size());
    }

}
