package ru.nsu.kosarev.db.building.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.BuildingType;

@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
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
