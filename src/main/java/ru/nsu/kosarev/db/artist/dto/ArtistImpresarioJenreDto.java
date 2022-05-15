package ru.nsu.kosarev.db.artist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@JsonDeserialize(builder = ArtistImpresarioJenreDto.ArtistImpresarioJenreDtoBuilder.class)
@Builder
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class ArtistImpresarioJenreDto {

    @Nullable
    @JsonProperty("id")
    private Integer id;

    @NonNull
    @JsonProperty("artistId")
    private Integer artistId;

    @NonNull
    @JsonProperty("impresarioId")
    private Integer impresarioId;

    @NonNull
    @JsonProperty("jenreId")
    private Integer jenreId;

}
