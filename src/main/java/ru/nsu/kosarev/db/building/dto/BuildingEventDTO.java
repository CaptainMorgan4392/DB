package ru.nsu.kosarev.db.building.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.kosarev.db.building.BuildingType;
import ru.nsu.kosarev.db.building.projections.BuildingEventProjection;

import java.util.List;

@AllArgsConstructor(onConstructor=@__(@JsonCreator))
@Getter
public class BuildingEventDTO {

    private String buildingName;

    private Integer buildingCapacity;

    private Integer buildingDiagonal;

    private String buildingAddress;

    private BuildingType buildingType;

    List<BuildingEventProjection> buildingEventProjectionList;

}
