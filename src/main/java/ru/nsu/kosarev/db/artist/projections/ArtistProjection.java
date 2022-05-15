package ru.nsu.kosarev.db.artist.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArtistProjection {

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

}
