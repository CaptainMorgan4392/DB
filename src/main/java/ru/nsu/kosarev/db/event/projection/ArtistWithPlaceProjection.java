package ru.nsu.kosarev.db.event.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArtistWithPlaceProjection {

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

    private Integer artistPlace;

}
