package ru.nsu.kosarev.db.artist.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ArtistImpresarioJenreProjection {

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

    private String impresarioName;

    private String impresarioSurname;

    private String impresarioBirthDate;

    private String jenre;

}
