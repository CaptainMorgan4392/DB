package ru.nsu.kosarev.db.impresario.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImpresarioArtistProjection {

    private Integer impresarioId;

    private String impresarioName;

    private String impresarioSurname;

    private String impresarioBirthDate;

    private Integer artistId;

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

}
