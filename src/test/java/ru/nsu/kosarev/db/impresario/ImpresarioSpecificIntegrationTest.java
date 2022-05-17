package ru.nsu.kosarev.db.impresario;

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
import ru.nsu.kosarev.db.impresario.dto.ArtistProjection;
import ru.nsu.kosarev.db.impresario.dto.ArtistsOfAllImpresariosDTO;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioArtistProjectionWithoutIds;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioProjection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ImpresarioSpecificIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArtistsWithImpresarioInMultipleJenres() throws URISyntaxException {
        int impresarioId = 1;

        final String baseUrl = "http://localhost:" + port + "/impresario/getArtistsInMultipleJenres/" + impresarioId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ImpresarioProjection>> request = new HttpEntity<>(null, headers);

        List<ImpresarioArtistProjectionWithoutIds> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ImpresarioArtistProjectionWithoutIds>>() {
                }
            ).getBody();

        List<String> artistNames = List.of("Andrey");
        List<String> artistSurnames = List.of("Nikolotov");
        List<String> artistBirthDates = List.of("31/12/2000");
        List<String> impresarioNames = List.of("Nikita");
        List<String> impresarioSurnames = List.of("Kosarev");
        List<String> impresarioBirthDates = List.of("30/10/2000");

        assertEquals(1, projections.size());

        assertEquals(artistNames.get(0), projections.get(0).getArtistName());
        assertEquals(artistSurnames.get(0), projections.get(0).getArtistSurname());
        assertEquals(artistBirthDates.get(0), projections.get(0).getArtistBirthDate());
        assertEquals(impresarioNames.get(0), projections.get(0).getImpresarioName());
        assertEquals(impresarioSurnames.get(0), projections.get(0).getImpresarioSurname());
        assertEquals(impresarioBirthDates.get(0), projections.get(0).getImpresarioBirthDate());

    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArtistsOfAllImpresarios() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/impresario/getArtistsOfAllImpresarios/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ImpresarioProjection>> request = new HttpEntity<>(null, headers);

        List<ArtistsOfAllImpresariosDTO> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ArtistsOfAllImpresariosDTO>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Nikita", "Marat", "Andrey", "Vasya");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev", "Nikolotov", "Pupkin");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000", "31/12/2000", "01/01/2000");
        List<List<ArtistProjection>> artists = Arrays.asList(
            Arrays.asList(
                new ArtistProjection(
                    "Marat",
                    "Pashentsev",
                    "24/05/2000"
                ),
                new ArtistProjection(
                    "Andrey",
                    "Nikolotov",
                    "31/12/2000"
                )
            ),
            Arrays.asList(
                new ArtistProjection(
                    "Nikita",
                    "Kosarev",
                    "30/10/2000"
                ),
                new ArtistProjection(
                    "Andrey",
                    "Nikolotov",
                    "31/12/2000"
                ),
                new ArtistProjection(
                    "Vasya",
                    "Pupkin",
                    "01/01/2000"
                )
            ),
            List.of(),
            List.of()
        );

        assertEquals(4, projections.size());

        for (int i = 0; i < 4; ++i) {
            assertEquals(names.get(i), projections.get(i).getImpresarioName());
            assertEquals(surnames.get(i), projections.get(i).getImpresarioSurname());
            assertEquals(birthDates.get(i), projections.get(i).getImpresarioBirthDate());

            assertEquals(artists.get(i).size(), projections.get(i).getArtistProjectionList().size());
            for (int j = 0; j < artists.get(i).size(); ++j) {
                assertEquals(
                    artists.get(i).get(j).getArtistName(),
                    projections.get(i).getArtistProjectionList().get(j).getArtistName()
                );
                assertEquals(
                    artists.get(i).get(j).getArtistSurname(),
                    projections.get(i).getArtistProjectionList().get(j).getArtistSurname()
                );
                assertEquals(
                    artists.get(i).get(j).getArtistBirthDate(),
                    projections.get(i).getArtistProjectionList().get(j).getArtistBirthDate()
                );
            }
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getImpresariosOfArtist() throws URISyntaxException {
        int artistId = 3;

        final String baseUrl = "http://localhost:" + port + "/impresario/getImpresariosOfArtist/" + artistId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ImpresarioProjection>> request = new HttpEntity<>(null, headers);

        List<ImpresarioProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ImpresarioProjection>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Nikita", "Marat");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000");

        assertEquals(2, projections.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), projections.get(i).getImpresarioName());
            assertEquals(surnames.get(i), projections.get(i).getImpresarioSurname());
            assertEquals(birthDates.get(i), projections.get(i).getImpresarioBirthDate());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getImpresariosInJenre() throws URISyntaxException {
        int jenreId = 8;

        final String baseUrl = "http://localhost:" + port + "/impresario/getImpresariosInJenre/" + jenreId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ImpresarioProjection>> request = new HttpEntity<>(null, headers);

        List<ImpresarioProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ImpresarioProjection>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Nikita", "Marat");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000");

        assertEquals(2, projections.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), projections.get(i).getImpresarioName());
            assertEquals(surnames.get(i), projections.get(i).getImpresarioSurname());
            assertEquals(birthDates.get(i), projections.get(i).getImpresarioBirthDate());
        }
    }

}
