package ru.nsu.kosarev.db.organizer.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerWithEventCountProjectionRowMapper implements RowMapper<OrganizerWithEventCountProjection> {

    @Nullable
    @Override
    public OrganizerWithEventCountProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrganizerWithEventCountProjection(
            rs.getString("organizerName"),
            rs.getString("organizerType"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("organizerBirthDate")),
            rs.getInt("eventCount")
        );
    }

}
