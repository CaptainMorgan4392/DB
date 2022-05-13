package ru.nsu.kosarev.db.impresario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ArtistsOfAllImpresariosDTO {

    private String impresarioName;

    private String impresarioSurname;

    private String impresarioBirthDate;

    private List<ArtistProjection> artistProjectionList;

}
