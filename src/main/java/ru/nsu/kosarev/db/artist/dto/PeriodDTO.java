package ru.nsu.kosarev.db.artist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@JsonDeserialize(builder = PeriodDTO.PeriodDTOBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class PeriodDTO {

    @NonNull
    @JsonProperty("from")
    private Date from;

    @NonNull
    @JsonProperty("to")
    private Date to;

}
