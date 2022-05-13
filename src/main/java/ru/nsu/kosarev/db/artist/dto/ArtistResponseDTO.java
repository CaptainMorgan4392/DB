package ru.nsu.kosarev.db.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public class ArtistResponseDTO {

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
