package ru.nsu.kosarev.db.building;

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
import ru.nsu.kosarev.db.building.dto.BuildingDTO;
import ru.nsu.kosarev.db.building.dto.BuildingResponseDTO;
import ru.nsu.kosarev.db.building.entity.Building;
import ru.nsu.kosarev.db.building.entity.Theatre;
import ru.nsu.kosarev.db.building.repository.BuildingRepository;
import ru.nsu.kosarev.db.building.repository.CinemaRepository;
import ru.nsu.kosarev.db.building.repository.ConcertSquareRepository;
import ru.nsu.kosarev.db.building.repository.CulturePalaceRepository;
import ru.nsu.kosarev.db.building.repository.StageRepository;
import ru.nsu.kosarev.db.building.repository.TheatreRepository;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BuildingCrudIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CulturePalaceRepository culturePalaceRepository;

    @Autowired
    private ConcertSquareRepository concertSquareRepository;

    @Test
    @Sql("init_building.sql")
    @Sql(scripts = "clear_building.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchAll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/building/fetch/list";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuildingSearchParams> request = new HttpEntity<>(new BuildingSearchParams(), headers);

        ResponseEntity<List<BuildingResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<BuildingResponseDTO>>() {}
            );

        List<BuildingResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(buildingResponseDTO ->
            buildingResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList(
            "Kakoi-to teatr",
            "Kakoi-to kinoteatr",
            "Kakoi-to culture dvorec",
            "Kakaya-to estrada"
        );

        List<Integer> capacities = Arrays.asList(
            3228,
            1488,
            3228,
            1488
        );

        List<Integer> diagonales = Arrays.asList(
            null,
            322,
            null,
            null
        );

        List<String> addresses = Arrays.asList(
            "Ulitsa Pushkina Dom Kolotushkina",
            "Ulitsa Esenina Dom Karuselina",
            "Ulitsa Pushkina Dom Kolotushkina",
            "Ulitsa Esenina Dom Karuselina"
        );
        List<BuildingType> buildingTypes = Arrays.asList(
            BuildingType.THEATRE,
            BuildingType.CINEMA,
            BuildingType.CULTURE_PALACE,
            BuildingType.STAGE
        );

        assertEquals(4, dtosWithoutIds.size());

        for (int i = 0; i < 4; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(capacities.get(i), dtosWithoutIds.get(i).getCapacity());
            assertEquals(diagonales.get(i), dtosWithoutIds.get(i).getDiagonal());
            assertEquals(addresses.get(i), dtosWithoutIds.get(i).getAddress());
            assertEquals(buildingTypes.get(i), dtosWithoutIds.get(i).getBuildingType());
        }
    }

    @Test
    @Sql("init_building.sql")
    @Sql(scripts = "clear_building.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchPage() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/building/fetch/page";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuildingSearchParams> request = new HttpEntity<>(
            new BuildingSearchParams(
                null,
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

        ResponseEntity<List<BuildingResponseDTO>> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<BuildingResponseDTO>>() {}
            );

        List<BuildingResponseDTO> dtosWithoutIds = responseEntity.getBody().stream().map(buildingResponseDTO ->
            buildingResponseDTO.toBuilder().id(null).build()
        ).collect(Collectors.toList());

        List<String> names = Arrays.asList(
            "Kakoi-to teatr",
            "Kakoi-to kinoteatr"
        );

        List<Integer> capacities = Arrays.asList(
            3228,
            1488
        );

        List<Integer> diagonales = Arrays.asList(
            null,
            322
        );

        List<String> addresses = Arrays.asList(
            "Ulitsa Pushkina Dom Kolotushkina",
            "Ulitsa Esenina Dom Karuselina"
        );
        List<BuildingType> buildingTypes = Arrays.asList(
            BuildingType.THEATRE,
            BuildingType.CINEMA
        );

        assertEquals(2, dtosWithoutIds.size());

        for (int i = 0; i < 2; ++i) {
            assertEquals(names.get(i), dtosWithoutIds.get(i).getName());
            assertEquals(capacities.get(i), dtosWithoutIds.get(i).getCapacity());
            assertEquals(diagonales.get(i), dtosWithoutIds.get(i).getDiagonal());
            assertEquals(addresses.get(i), dtosWithoutIds.get(i).getAddress());
            assertEquals(buildingTypes.get(i), dtosWithoutIds.get(i).getBuildingType());
        }
    }

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchEventPlaceByEventId() throws URISyntaxException {
        int eventId = 1;

        final String baseUrl = "http://localhost:" + port + "/building/fetch/fetchByEventId/" + eventId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(
            null,
            headers
        );

        ResponseEntity<Integer> responseEntity =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                Integer.class
            );

        assertEquals(1, responseEntity.getBody());
    }

    @Test
    @Sql("init_building.sql")
    @Sql(scripts = "clear_building.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void create() throws URISyntaxException {
        assertEquals(4, buildingRepository.findAll().size());
        assertEquals(1, theatreRepository.findAll().size());

        final String baseUrl = "http://localhost:" + port + "/building/add";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuildingDTO> request = new HttpEntity<>(
            new BuildingDTO(
                null,
                "Obshaga",
                3228,
                null,
                "Pirogova, 18",
                    BuildingType.THEATRE
                ),
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            BuildingResponseDTO.class
        );

        assertEquals(5, buildingRepository.findAll().size());
        assertEquals(2, theatreRepository.findAll().size());
    }

    @Test
    @Sql("init_building.sql")
    @Sql(scripts = "clear_building.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void update() throws URISyntaxException {
        List<Building> found = buildingRepository.findAll();
        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/building/update";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        BuildingDTO toUpdate = new BuildingDTO(
            fetchedId,
            "Obshaga",
            3228,
            null,
            "Pirogova, 18",
            BuildingType.THEATRE
        );

        HttpEntity<BuildingDTO> request = new HttpEntity<>(
            toUpdate,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            BuildingResponseDTO.class
        );

        Building building = buildingRepository.findById(fetchedId).get();
        Theatre theatre = theatreRepository.findById(fetchedId).get();

        assertEquals(toUpdate.getId(), building.getId());
        assertEquals(toUpdate.getName(), building.getName());
        assertEquals(toUpdate.getCapacity(), building.getCapacity());

        assertNull(toUpdate.getDiagonal());
        assertEquals(toUpdate.getAddress(), theatre.getAddress());
        assertEquals(toUpdate.getBuildingType().name(), building.getBuildingType());
    }

    @Test
    @Sql("init_building.sql")
    @Sql(scripts = "clear_building.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void delete() throws URISyntaxException {
        List<Building> found = buildingRepository.findAll();
        List<Theatre> foundTheatres = theatreRepository.findAll();
        assertEquals(4, found.size());
        assertEquals(1, foundTheatres.size());

        int fetchedId = found.get(0).getId();

        final String baseUrl = "http://localhost:" + port + "/building/delete/" + fetchedId;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuildingDTO> request = new HttpEntity<>(
            null,
            headers
        );

        testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            request,
            Object.class
        );

        assertEquals(3, buildingRepository.findAll().size());
        assertEquals(0, theatreRepository.findAll().size());
    }

}
