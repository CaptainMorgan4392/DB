package ru.nsu.kosarev.db.impresario.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

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

    public Pair<Integer, Integer> getIds() {
        return Pair.of(
            this.getImpresarioId(),
            this.getArtistId()
        );
    }

}
