package ru.nsu.kosarev.db.building.sortingfilter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.BuildingType;

@Data
@JsonDeserialize(builder = BuildingSearchParams.BuildingSearchParamsBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
@NoArgsConstructor
public class BuildingSearchParams {

    @Nullable
    @JsonProperty("name")
    private String name;

    @Nullable
    @JsonProperty("capacity")
    private Integer capacity;

    @Nullable
    @JsonProperty("diagonal")
    private Float diagonal;

    @Nullable
    @JsonProperty("address")
    private String address;

    @Nullable
    @JsonProperty("buildingType")
    private BuildingType buildingType;

    @Nullable
    @JsonProperty("sortBy")
    private String sortBy;

    @Nullable
    @JsonProperty("pageNum")
    private Integer pageNumber;

    @Nullable
    @JsonProperty("pageSize")
    private Integer pageSize;

}
