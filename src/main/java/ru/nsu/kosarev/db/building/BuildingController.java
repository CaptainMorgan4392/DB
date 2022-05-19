package ru.nsu.kosarev.db.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.building.dto.BuildingDTO;
import ru.nsu.kosarev.db.building.dto.BuildingEventDTO;
import ru.nsu.kosarev.db.building.dto.BuildingResponseDTO;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping(value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public BuildingResponseDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
        if (buildingDTO.getId() != null && buildingService.isAlreadyExists(buildingDTO)) {
            return null;
        }

        return buildingService.saveBuilding(buildingDTO);
    }

    @PostMapping(value = "/fetch/page",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BuildingResponseDTO> fetchBuildingsPage(@RequestBody BuildingSearchParams buildingSearchParams) {
        if (buildingSearchParams.getPageNumber() == null || buildingSearchParams.getPageSize() == null) {
            return null;
        }

        return buildingService.fetchBuildingsPage(buildingSearchParams);
    }

    @PostMapping(value = "/fetch/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BuildingResponseDTO> fetchBuildingsList(@RequestBody BuildingSearchParams buildingSearchParams) {
        return buildingService.fetchBuildingsList(buildingSearchParams);
    }

    @PostMapping(value = "/fetch/fetchByEventId/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer fetchEventPlaceByEventId(@PathVariable("id") Integer eventId) {
        return buildingService.fetchEventPlaceByEventId(eventId);
    }

    @PostMapping(value = "/update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public BuildingResponseDTO updateBuilding(@RequestBody BuildingDTO buildingDTO) {
        if (buildingDTO.getId() == null || !buildingService.isAlreadyExists(buildingDTO)) {
            return null;
        }

        return buildingService.saveBuilding(buildingDTO);
    }

    @PostMapping(value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBuilding(@PathVariable("id") Integer buildingId) {
        buildingService.deleteBuilding(buildingId);
    }

    @PostMapping(value = "/buildingsWithEvents",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    List<BuildingEventDTO> fetchBuildingsWithEvents() {
        return buildingService.fetchBuildingsWithEvents();
    }

}
