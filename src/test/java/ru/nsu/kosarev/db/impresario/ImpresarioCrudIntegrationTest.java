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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import ru.nsu.kosarev.db.DbApplication;
import ru.nsu.kosarev.db.artist.Artist;
import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistResponseDTO;
import ru.nsu.kosarev.db.artist.sortingfilter.ArtistSearchParams;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioResponseDTO;
import ru.nsu.kosarev.db.impresario.repository.ImpresarioRepository;
import ru.nsu.kosarev.db.impresario.sortingfilter.ImpresarioSearchParams;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImpresarioCrudIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ImpresarioRepository impresarioRepository;

    @Test
    @Sql("init_impresario.sql")
    @Sql(scripts = "clear_impresario.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchAll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/impresario/fetch/list";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImpresarioSearchParams> request = new HttpEntity<>(
            new ImpresarioSearchParams(),
            headers
        );

        ResponseEntity<List<ImpresarioResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ImpresarioResponseDTO>>() {}
            );

        List<ImpresarioResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(impresarioResponseDTO ->
            impresarioResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList("Nikita", "Marat", "Andrey", "Vasya");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev", "Nikolotov", "Pupkin");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000", "31/12/2000", "01/01/2000");

        assertEquals(4, dtosWithoutIds.size());

        for (int i = 0; i < 4; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(surnames.get(i), dtosWithoutIds.get(i).getSurname());
            assertEquals(birthDates.get(i), dtosWithoutIds.get(i).getFormattedDate());
        }
    }

    @Test
    @Sql("init_impresario.sql")
    @Sql(scripts = "clear_impresario.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchPage() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/impresario/fetch/page";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImpresarioSearchParams> request = new HttpEntity<>(
            new ImpresarioSearchParams(
                null,
                null,
                null,
                null,
                0,
                2
            ),
            headers
        );

        ResponseEntity<List<ImpresarioResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<ImpresarioResponseDTO>>() {}
            );

        List<ImpresarioResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(impresarioResponseDTO ->
            impresarioResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList("Nikita", "Marat");
        List<String> surnames = Arrays.asList("Kosarev", "Pashentsev");
        List<String> birthDates = Arrays.asList("30/10/2000", "24/05/2000");

        assertEquals(2, dtosWithoutIds.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(surnames.get(i), dtosWithoutIds.get(i).getSurname());
            assertEquals(birthDates.get(i), dtosWithoutIds.get(i).getFormattedDate());
        }
    }

    @Test
    @Sql("init_impresario.sql")
    @Sql(scripts = "clear_impresario.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void create() throws URISyntaxException {
        assertEquals(4, impresarioRepository.findAll().size());

        final String baseUrl = "http://localhost:" + port + "/impresario/add";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImpresarioDTO> request = new HttpEntity<>(
            new ImpresarioDTO(
                null,
                "Dima",
                "Korolyov",
                Date.valueOf("2001-12-31")
            ),
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            ImpresarioResponseDTO.class
        );

        assertEquals(5, impresarioRepository.findAll().size());
    }

    @Test
    @Sql("init_impresario.sql")
    @Sql(scripts = "clear_impresario.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void update() throws URISyntaxException {
        List<Impresario> found = impresarioRepository.findAll();
        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/impresario/update";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ImpresarioDTO toUpdate = new ImpresarioDTO(
            fetchedId,
            "Dima",
            "Korolyov",
            Date.valueOf("2000-01-01")
        );

        HttpEntity<ImpresarioDTO> request = new HttpEntity<>(
            toUpdate,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            ImpresarioResponseDTO.class
        );

        Impresario artist = impresarioRepository.findById(fetchedId).get();

        assertEquals(toUpdate.getId(), artist.getId());
        assertEquals(toUpdate.getName(), artist.getName());
        assertEquals(toUpdate.getSurname(), artist.getSurname());
        assertEquals(toUpdate.getBirthDate(), artist.getBirthDate());
    }

    @Test
    @Sql("init_impresario.sql")
    @Sql(scripts = "clear_impresario.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete() throws URISyntaxException {
        List<Impresario> found = impresarioRepository.findAll();
        assertEquals(4, found.size());
        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/impresario/delete/" + fetchedId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImpresarioDTO> request = new HttpEntity<>(
            null,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            Object.class
        );

        assertEquals(3, impresarioRepository.findAll().size());
    }

}
