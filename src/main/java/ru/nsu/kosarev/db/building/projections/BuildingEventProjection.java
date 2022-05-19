package ru.nsu.kosarev.db.building.projections;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@Setter
public class BuildingEventProjection {

    private Integer buildingId;

    private String name;

    private String eventType;

    private String eventPlace;

    private String eventDate;

}
