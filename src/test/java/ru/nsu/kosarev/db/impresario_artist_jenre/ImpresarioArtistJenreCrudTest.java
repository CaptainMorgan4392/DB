package ru.nsu.kosarev.db.impresario_artist_jenre;

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
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.organizer.dto.OrganizerDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImpresarioArtistJenreCrudTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("init_impresario_artist_jenre.sql")
    @Sql(scripts = "clear_impresario_artist_jenre.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchAll() throws URISyntaxException {
        assertEquals(5, fetchCurrentLines().size());
    }

    @Test
    @Sql("init_impresario_artist_jenre.sql")
    @Sql(scripts = "clear_impresario_artist_jenre.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void create() throws URISyntaxException {
        assertEquals(5, fetchCurrentLines().size());

        final String baseUrl = "http://localhost:" + port + "/artist/artistWithImpresario/add/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ArtistImpresarioJenreDto> request = new HttpEntity<>(
            new ArtistImpresarioJenreDto(
                null,
                1,
                1,
                8
            ),
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            Object.class
        );

        assertEquals(6, fetchCurrentLines().size());
    }

    @Test
    @Sql("init_impresario_artist_jenre.sql")
    @Sql(scripts = "clear_impresario_artist_jenre.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete() throws URISyntaxException {
        assertEquals(5, fetchCurrentLines().size());

        final String baseUrl = "http://localhost:" + port + "/artist/artistWithImpresario/delete/1/2/8";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ArtistImpresarioJenreDto> request = new HttpEntity<>(
            null,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            Object.class
        );

        assertEquals(4, fetchCurrentLines().size());
    }

    private List<ArtistImpresarioJenreProjection> fetchCurrentLines() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/artist/artistWithImpresario/fetch/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrganizerDTO> request = new HttpEntity<>(
            null,
            headers
        );

        return testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<List<ArtistImpresarioJenreProjection>>() {
            }
        ).getBody();
    }

}
