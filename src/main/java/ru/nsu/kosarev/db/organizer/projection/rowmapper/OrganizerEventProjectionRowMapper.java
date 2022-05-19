package ru.nsu.kosarev.db.organizer.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.organizer.projection.OrganizerEventProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerEventProjectionRowMapper implements RowMapper<OrganizerEventProjection> {

    @Nullable
    @Override
    public OrganizerEventProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrganizerEventProjection(
            rs.getInt("organizerId"),
            rs.getString("organizerName"),
            rs.getString("organizerSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("organizerDate")),
            rs.getInt("eventId"),
            rs.getString("eventName"),
            rs.getString("eventType"),
            rs.getString("eventPlace"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("eventDate"))
        );
    }

}
