package ru.nsu.kosarev.db.organizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public class OrganizerResponseDTO {

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
    @JsonProperty("formattedDate")
    private String formattedDate;

}
