package ru.nsu.kosarev.db.event.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@JsonDeserialize(builder = EventDTO.EventDTOBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class EventDTO {

    @Nullable
    @JsonProperty("id")
    private Integer id;

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("eventType")
    private Integer eventType;

    @NonNull
    @JsonProperty("eventPlace")
    private Integer eventPlace;

    @NonNull
    @JsonProperty("eventDate")
    private Date eventDate;

}
