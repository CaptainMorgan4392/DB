package ru.nsu.kosarev.db.impresario.sortingfilter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@JsonDeserialize(builder = ImpresarioSearchParams.ImpresarioSearchParamsBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
@NoArgsConstructor
public class ImpresarioSearchParams {

    @Nullable
    @JsonProperty("name")
    private String name;

    @Nullable
    @JsonProperty("surname")
    private String surname;

    @Nullable
    @JsonProperty("birthDate")
    private Date birthDate;

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
