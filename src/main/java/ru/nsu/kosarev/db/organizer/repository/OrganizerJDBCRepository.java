package ru.nsu.kosarev.db.organizer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.organizer.projection.OrganizerEventProjection;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;
import ru.nsu.kosarev.db.organizer.projection.rowmapper.OrganizerWithEventCountProjectionRowMapper;

import java.util.Date;
import java.util.List;

@Repository
public class OrganizerJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //11
    public List<OrganizerWithEventCountProjection> getOrganizersWithEventCountsInPeriod(Date from, Date to) {
        return jdbcTemplate.query(
            "SELECT x.name AS organizerName, " +
                "x.surname AS organizerSurname, " +
                "x.birthdate AS organizerBirthDate, " +
                "count(*) OVER (PARTITION BY x.id) AS eventCount " +
                "FROM " +
                "(SELECT o.* " +
                "FROM organizer o " +
                "INNER JOIN organizer_event oe on o.id = oe.organizer " +
                "INNER JOIN event e on e.id = oe.event " +
                "WHERE e.eventdate BETWEEN ? AND ?) AS x ",
            new OrganizerWithEventCountProjectionRowMapper(),
            from,
            to
        );
    }

    public void bindOrganizerToEvent(Integer organizerId, Integer eventId) {
        //TODO
    }

    public List<OrganizerEventProjection> getEventsOfOrganizer(Integer organizerId) {
        //TODO

        return null;
    }

    public void deleteEventOfOrganizer(Integer organizerId, Integer eventId) {
        //TODO
    }

}
