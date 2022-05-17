package ru.nsu.kosarev.db.impresario.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImpresarioArtistProjectionWithoutIds {

    private String impresarioName;

    private String impresarioSurname;

    private String impresarioBirthDate;

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

}
