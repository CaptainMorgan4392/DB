package ru.nsu.kosarev.db.event.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.event.projection.ArtistWithPlaceProjection;
import ru.nsu.kosarev.db.event.projection.EventProjection;
import ru.nsu.kosarev.db.event.projection.rowmapper.ArtistWithPlaceProjectionRowMapper;
import ru.nsu.kosarev.db.event.projection.rowmapper.EventProjectionRowMapper;

import java.util.Date;
import java.util.List;

@Repository
public class EventJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //6
    public List<EventProjection> getEventsInPeriodOrByOrganizer(Date from, Date to, Integer organizerId) {
        return jdbcTemplate.query(
            "SELECT DISTINCT ON(e.id) e.name as eventName, " +
                "et.eventtype as eventType, " +
                "b.name as buildingName, " +
                "e.eventdate as eventDate " +
                "FROM event e " +
                "INNER JOIN organizer_event oe on e.id = oe.event " +
                "INNER JOIN building b on b.id = e.eventplace " +
                "INNER JOIN event_type et on et.id = e.eventtype " +
                "WHERE e.eventdate BETWEEN ? AND ? OR oe.organizer = ? " +
                "ORDER BY e.id",
            new EventProjectionRowMapper(),
            from,
            to,
            organizerId
        );
    }

    //7
    public List<ArtistWithPlaceProjection> getArtistsWithPlaces(Integer eventId) {
        return jdbcTemplate.query(
            "SELECT a.name as artistName, " +
                "a.surname as artistSurname, " +
                "a.birthdate as artistBirthDate, " +
                "p.place as artistPlace " +
                "FROM event e " +
                "INNER JOIN artist_event ae on e.id = ae.event " +
                "INNER JOIN artist a on a.id = ae.artist " +
                "INNER JOIN artist_place ap on ae.id = ap.artistevent " +
                "INNER JOIN place p on p.id = ap.place " +
                "WHERE e.id = ?",
            new ArtistWithPlaceProjectionRowMapper(),
            eventId
        );
    }

    //8
    public List<EventProjection> getEventsInBuilding(Integer buildingId) {
        return jdbcTemplate.query(
            "SELECT e.name AS eventName, " +
                "et.eventtype AS eventType, " +
                "b.name AS buildingName, " +
                "e.eventDate AS eventDate " +
                "FROM event e " +
                "INNER JOIN building b on b.id = e.eventplace " +
                "INNER JOIN event_type et on et.id = e.eventtype " +
                "WHERE b.id = ?",
            new EventProjectionRowMapper(),
            buildingId
        );
    }

}
