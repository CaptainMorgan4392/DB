package ru.nsu.kosarev.db.artist.projections;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@Setter
public class ArtistEventProjection {

    private String artistName;

    private String artistSurname;

    private String artistBirthDate;

    private String eventName;

    private String eventType;

    private String eventPlace;

    private String eventDate;

}
