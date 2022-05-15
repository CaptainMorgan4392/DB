package ru.nsu.kosarev.db.organizer.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrganizerWithEventCountProjection {

    private String organizerName;

    private String organizerSurname;

    private String organizerBirthDate;

    private Integer eventCount;

}
