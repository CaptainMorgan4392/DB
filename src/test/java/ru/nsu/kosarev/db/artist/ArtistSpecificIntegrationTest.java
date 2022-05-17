package ru.nsu.kosarev.db.artist;

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
import ru.nsu.kosarev.db.artist.dto.PeriodDTO;
import ru.nsu.kosarev.db.artist.projections.ArtistProjection;

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
public class ArtistSpecificIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArtistsInJenre() throws URISyntaxException {
        int jenreId = 8;

        final String baseUrl = "http://localhost:" + port + "/artist/artistsInJenre/" + jenreId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ArtistProjection>> request = new HttpEntity<>(null, headers);

        List<ArtistProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ArtistProjection>>() {
                }
            ).getBody();

        List<String> names = Arrays.asList("Nikita", "Marat", "Andrey");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev", "Nikolotov");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000", "31/12/2000");

        assertEquals(3, projections.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), projections.get(i).getArtistName());
            assertEquals(surnames.get(i), projections.get(i).getArtistSurname());
            assertEquals(birthDates.get(i), projections.get(i).getArtistBirthDate());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArtistsNotTakingPartInPeriod() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/artist/artistsNotTakingPartInPeriod/";
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

        List<ArtistProjection> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ArtistProjection>>() {
                }
            ).getBody();


        List<String> names = Arrays.asList("Andrey", "Vasya");
        List<String> surnames = Arrays.asList("Nikolotov", "Pupkin");
        List<String> birthDates = Arrays.asList("31/12/2000", "01/01/2000");

        assertEquals(2, projections.size());
        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), projections.get(i).getArtistName());
            assertEquals(surnames.get(i), projections.get(i).getArtistSurname());
            assertEquals(birthDates.get(i), projections.get(i).getArtistBirthDate());
        }
    }

}
