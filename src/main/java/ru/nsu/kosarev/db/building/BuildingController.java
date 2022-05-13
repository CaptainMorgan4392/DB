package ru.nsu.kosarev.db.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.building.dto.BuildingDTO;
import ru.nsu.kosarev.db.building.dto.BuildingResponseDTO;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PutMapping(value = "/add")
    public BuildingResponseDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
        if (buildingDTO.getId() != null && buildingService.isAlreadyExists(buildingDTO)) {
            return null;
        }

        return buildingService.saveBuilding(buildingDTO);
    }

    @GetMapping(value = "/fetch/page")
    public List<BuildingResponseDTO> fetchBuildingsPage(@RequestBody BuildingSearchParams buildingSearchParams) {
        if (buildingSearchParams.getPageNumber() == null || buildingSearchParams.getPageSize() == null) {
            return null;
        }

        return buildingService.fetchBuildingsPage(buildingSearchParams);
    }

    @GetMapping(value = "/fetch/list")
    public List<BuildingResponseDTO> fetchBuildingsList(@RequestBody BuildingSearchParams buildingSearchParams) {
        return buildingService.fetchBuildingsList(buildingSearchParams);
    }

    @PatchMapping(value = "/update")
    public BuildingResponseDTO updateBuilding(@RequestBody BuildingDTO buildingDTO) {
        if (buildingDTO.getId() == null || !buildingService.isAlreadyExists(buildingDTO)) {
            return null;
        }

        return buildingService.saveBuilding(buildingDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteBuilding(@PathVariable("id") Integer buildingId) {
        buildingService.deleteBuilding(buildingId);
    }

}
