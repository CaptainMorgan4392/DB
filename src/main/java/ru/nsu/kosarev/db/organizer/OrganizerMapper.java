package ru.nsu.kosarev.db.organizer;

import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.organizer.dto.OrganizerDTO;
import ru.nsu.kosarev.db.organizer.dto.OrganizerResponseDTO;

public class OrganizerMapper {

    static OrganizerResponseDTO toOrganizerDTO(Organizer organizer) {
        return new OrganizerResponseDTO(
            organizer.getId(),
            organizer.getName(),
            organizer.getSurname(),
            DateTimeFormatter.getFormattedDateFromTimestamp(organizer.getBirthDate())
        );
    }

    static Organizer toOrganizer(OrganizerDTO organizerDTO) {
        return new Organizer(
            organizerDTO.getId(),
            organizerDTO.getName(),
            organizerDTO.getSurname(),
            organizerDTO.getBirthDate()
        );
    }

}
