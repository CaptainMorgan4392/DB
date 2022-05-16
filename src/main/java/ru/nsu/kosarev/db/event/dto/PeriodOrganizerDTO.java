package ru.nsu.kosarev.db.event.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@JsonDeserialize(builder = PeriodOrganizerDTO.PeriodOrganizerDTOBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class PeriodOrganizerDTO {

    @NonNull
    @JsonProperty("from")
    private Date from;

    @NonNull
    @JsonProperty("to")
    private Date to;

    @NonNull
    @JsonProperty("organizerId")
    private Integer id;

}
