package ru.nsu.kosarev.db.building.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.BuildingType;

@AllArgsConstructor
public class BuildingResponseDTO {

    @Nullable
    @JsonProperty("id")
    private Integer id;

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("capacity")
    private Integer capacity;

    @Nullable
    @JsonProperty("diagonal")
    private Integer diagonal;

    @Nullable
    @JsonProperty("address")
    private String address;

    @NonNull
    @JsonProperty("buildingType")
    private BuildingType buildingType;

}
