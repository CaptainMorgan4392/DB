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
import org.springframework.test.context.jdbc.Sql;
import ru.nsu.kosarev.db.DbApplication;
import ru.nsu.kosarev.db.building.dto.BuildingEventDTO;
import ru.nsu.kosarev.db.building.projections.BuildingEventProjection;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = DbApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BuildingSpecificIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("../init_specific_tests.sql")
    @Sql(scripts = "../clear_specific_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void fetchBuildingsWithEvents() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/building/buildingsWithEvents/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(
            null,
            headers
        );

        List<BuildingEventDTO> projections =
            testRestTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<BuildingEventDTO>>() {
                }
            ).getBody();

        List<String> buildingNames = Arrays.asList("Kakoi-to teatr", "Kakoi-to kinoteatr");
        List<Integer> buildingCapacities = Arrays.asList(3228, 1488);
        List<Integer> buildingDiagonales = Arrays.asList(null, 322);
        List<String> buildingAddresses = Arrays.asList("Ulitsa Pushkina Dom Kolotushkina", "Ulitsa Esenina Dom Karuselina");
        List<BuildingType> buildingTypes = Arrays.asList(BuildingType.THEATRE, BuildingType.CINEMA);
        List<List<BuildingEventProjection>> buildingEventProjections = Arrays.asList(
            List.of(
                new BuildingEventProjection(
                    1,
                    "Concert of Pushkin",
                    "CONCERT",
                    "Kakoi-to teatr",
                    "01/01/2100"
                ),
                new BuildingEventProjection(
                    1,
                    "Opera of Kolotushkin",
                    "PERFORMANCE",
                    "Kakoi-to teatr",
                    "01/02/2100"
                )
            ),
            List.of(
                new BuildingEventProjection(
                    2,
                    "Musicle of Pushkin",
                    "FILM",
                    "Kakoi-to kinoteatr",
                    "01/03/2100"
                ),
                new BuildingEventProjection(
                    2,
                    "Triller of Kolotushkin",
                    "FILM",
                    "Kakoi-to kinoteatr",
                    "01/04/2100"
                )
            )
        );

        assertEquals(2, projections.size());
        for (int i = 0; i < 2; ++i) {
            assertEquals(buildingNames.get(i), projections.get(i).getBuildingName());
            assertEquals(buildingCapacities.get(i), projections.get(i).getBuildingCapacity());
            assertEquals(buildingDiagonales.get(i), projections.get(i).getBuildingDiagonal());
            assertEquals(buildingAddresses.get(i), projections.get(i).getBuildingAddress());
            assertEquals(buildingTypes.get(i), projections.get(i).getBuildingType());
            assertEquals(buildingEventProjections.get(i).size(), projections.get(i).getBuildingEventProjectionList().size());

            for (int j = 0; j < buildingEventProjections.get(i).size(); ++j) {
                assertEquals(
                    buildingEventProjections.get(i).get(j).getBuildingId(),
                    projections.get(i).getBuildingEventProjectionList().get(j).getBuildingId()
                );
                assertEquals(
                    buildingEventProjections.get(i).get(j).getName(),
                    projections.get(i).getBuildingEventProjectionList().get(j).getName()
                );
                assertEquals(
                    buildingEventProjections.get(i).get(j).getEventType(),
                    projections.get(i).getBuildingEventProjectionList().get(j).getEventType()
                );
                assertEquals(
                    buildingEventProjections.get(i).get(j).getEventPlace(),
                    projections.get(i).getBuildingEventProjectionList().get(j).getEventPlace()
                );
                assertEquals(
                    buildingEventProjections.get(i).get(j).getEventDate(),
                    projections.get(i).getBuildingEventProjectionList().get(j).getEventDate()
                );
            }
        }
    }

}
