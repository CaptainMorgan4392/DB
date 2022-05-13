package ru.nsu.kosarev.db.impresario.dto;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.kosarev.db.impresario.projections.ImpresarioArtistProjection;

@Getter
@Setter
public class ArtistProjection {

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

    public ArtistProjection(ImpresarioArtistProjection impresarioArtistProjection) {
        this.artistName = impresarioArtistProjection.getArtistName();
        this.artistSurname = impresarioArtistProjection.getArtistSurname();
        this.artistBirthDate = impresarioArtistProjection.getArtistBirthDate();
    }

}
