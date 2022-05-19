package ru.nsu.kosarev.db.artist.projections;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@Setter
public class ArtistEventProjection {

    private Integer artistId;

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

    private Integer eventId;

    private String eventName;

    private String eventType;

    private String eventPlace;

    private String eventDate;

}
