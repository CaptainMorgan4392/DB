package ru.nsu.kosarev.db.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class EventResponseDTO {

    @Nullable
    @JsonProperty("id")
    private Integer id;

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("eventType")
    private String eventType;

    @NonNull
    @JsonProperty("eventPlace")
    private String eventPlace;

    @NonNull
    @JsonProperty("eventDate")
    private String eventDate;

}
