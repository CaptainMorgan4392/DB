package ru.nsu.kosarev.db.artist;

import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistResponseDTO;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;

public class ArtistMapper {

    static ArtistResponseDTO toArtistDto(Artist artist) {
        return new ArtistResponseDTO(
            artist.getId(),
            artist.getName(),
            artist.getSurname(),
            DateTimeFormatter.getFormattedDateFromTimestamp(artist.getBirthDate())
        );
    }

    static Artist toArtist(ArtistDTO artistDTO) {
        return new Artist(
            artistDTO.getId(),
            artistDTO.getName(),
            artistDTO.getSurname(),
            artistDTO.getBirthDate()
        );
    }

}
