package ru.nsu.kosarev.db.building.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.building.BuildingType;

@Data
@JsonDeserialize(builder = BuildingDTO.BuildingDTOBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class BuildingDTO {

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
