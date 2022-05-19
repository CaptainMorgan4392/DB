package ru.nsu.kosarev.db.organizer.projection;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@Setter
public class OrganizerEventProjection {

    private Integer organizerId;

    private String organizerName;

    private String organizerSurname;

    private String organizerBirthDate;

    private Integer eventId;

    private String eventName;

    private String eventType;

    private String eventPlace;

    private String eventDate;

}
