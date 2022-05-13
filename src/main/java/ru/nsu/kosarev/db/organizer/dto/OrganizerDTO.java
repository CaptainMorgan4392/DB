package ru.nsu.kosarev.db.organizer.dto;

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
@JsonDeserialize(builder = OrganizerDTO.OrganizerDTOBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class OrganizerDTO {

    @Nullable
    @JsonProperty("id")
    private Integer id;

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("surname")
    private String surname;

    @NonNull
    @JsonProperty("birthDate")
    private Date birthDate;

}