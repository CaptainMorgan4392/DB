package ru.nsu.kosarev.db.building;

import org.springframework.data.util.Pair;
import ru.nsu.kosarev.db.building.dto.BuildingDTO;
import ru.nsu.kosarev.db.building.dto.BuildingResponseDTO;
import ru.nsu.kosarev.db.building.entity.Building;
import ru.nsu.kosarev.db.building.entity.Cinema;
import ru.nsu.kosarev.db.building.entity.ConcertSquare;
import ru.nsu.kosarev.db.building.entity.CulturePalace;
import ru.nsu.kosarev.db.building.entity.Stage;
import ru.nsu.kosarev.db.building.entity.Theatre;

public class BuildingMapper {

    static BuildingResponseDTO compose(Building building, Theatre theatre) {
        return new BuildingResponseDTO(
            building.getId(),
            building.getName(),
            building.getCapacity(),
            null,
            theatre.getAddress(),
            BuildingType.THEATRE
        );
    }

    static BuildingResponseDTO compose(Building building, Stage stage) {
        return new BuildingResponseDTO(
            building.getId(),
            building.getName(),
            building.getCapacity(),
            null,
            stage.getAddress(),
            BuildingType.STAGE
        );
    }

    static BuildingResponseDTO compose(Building building, Cinema cinema) {
        return new BuildingResponseDTO(
            building.getId(),
            building.getName(),
            building.getCapacity(),
            cinema.getDiagonal(),
            cinema.getAddress(),
            BuildingType.CINEMA
        );
    }

    static BuildingResponseDTO compose(Building building, ConcertSquare concertSquare) {
        return new BuildingResponseDTO(
            building.getId(),
            building.getName(),
            building.getCapacity(),
            null,
            null,
            BuildingType.CONCERT_SQUARE
        );
    }

    static BuildingResponseDTO compose(Building building, CulturePalace culturePalace) {
        return new BuildingResponseDTO(
            building.getId(),
            building.getName(),
            building.getCapacity(),
            null,
            culturePalace.getAddress(),
            BuildingType.CULTURE_PALACE
        );
    }

    static Pair<Building, Theatre> decomposeTheatre(BuildingDTO buildingDTO) {
        return Pair.of(
            new Building(
                buildingDTO.getId(),
                buildingDTO.getName(),
                buildingDTO.getCapacity(),
                buildingDTO.getBuildingType().name()
            ),
            new Theatre(
                buildingDTO.getId(),
                buildingDTO.getAddress()
            )
        );
    }

    static Pair<Building, Stage> decomposeStage(BuildingDTO buildingDTO) {
        return Pair.of(
            new Building(
                buildingDTO.getId(),
                buildingDTO.getName(),
                buildingDTO.getCapacity(),
                buildingDTO.getBuildingType().name()
            ),
            new Stage(
                buildingDTO.getId(),
                buildingDTO.getAddress()
            )
        );
    }

    static Pair<Building, Cinema> decomposeCinema(BuildingDTO buildingDTO) {
        return Pair.of(
            new Building(
                buildingDTO.getId(),
                buildingDTO.getName(),
                buildingDTO.getCapacity(),
                buildingDTO.getBuildingType().name()
            ),
            new Cinema(
                buildingDTO.getId(),
                buildingDTO.getDiagonal(),
                buildingDTO.getAddress()
            )
        );
    }

    static Pair<Building, ConcertSquare> decomposeConcertSquare(BuildingDTO buildingDTO) {
        return Pair.of(
            new Building(
                buildingDTO.getId(),
                buildingDTO.getName(),
                buildingDTO.getCapacity(),
                buildingDTO.getBuildingType().name()
            ),
            new ConcertSquare(
                buildingDTO.getId()
            )
        );
    }

    static Pair<Building, CulturePalace> decomposeCulturePalace(BuildingDTO buildingDTO) {
        return Pair.of(
            new Building(
                buildingDTO.getId(),
                buildingDTO.getName(),
                buildingDTO.getCapacity(),
                buildingDTO.getBuildingType().name()
            ),
            new CulturePalace(
                buildingDTO.getId(),
                buildingDTO.getAddress()
            )
        );
    }

}
