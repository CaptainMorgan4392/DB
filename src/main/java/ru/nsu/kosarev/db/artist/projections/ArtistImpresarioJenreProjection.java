package ru.nsu.kosarev.db.artist.projections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@JsonDeserialize(builder = ArtistImpresarioJenreProjection.ArtistImpresarioJenreProjectionBuilder.class)
@Builder(toBuilder = true)
@Getter
@Setter
public class ArtistImpresarioJenreProjection {

    private Integer artistId;

    @JsonProperty("artistName")
    private String artistName;

    @JsonProperty("artistSurname")
    private String artistSurname;

    @JsonProperty("artistBirthDate")
    private String artistBirthDate;

    private Integer impresarioId;

    @JsonProperty("impresarioName")
    private String impresarioName;

    @JsonProperty("impresarioSurname")
    private String impresarioSurname;

    @JsonProperty("impresarioBirthDate")
    private String impresarioBirthDate;

    private Integer jenreId;

    @JsonProperty("jenre")
    private String jenre;

}
