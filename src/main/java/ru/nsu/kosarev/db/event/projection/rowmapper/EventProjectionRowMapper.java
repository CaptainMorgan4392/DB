package ru.nsu.kosarev.db.event.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.event.projection.EventProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventProjectionRowMapper implements RowMapper<EventProjection> {

    @Nullable
    @Override
    public EventProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventProjection(
            rs.getString("eventName"),
            rs.getString("eventType"),
            rs.getString("buildingName"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("eventDate"))
        );
    }

}
