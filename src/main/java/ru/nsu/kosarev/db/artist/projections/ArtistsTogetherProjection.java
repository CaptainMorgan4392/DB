package ru.nsu.kosarev.db.artist.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArtistsTogetherProjection {

    private String firstArtistName;

    private String firstArtistSurname;

    private String firstArtistBirthDate;

    private String secondArtistName;

    private String secondArtistSurname;

    private String secondArtistBirthDate;

}
