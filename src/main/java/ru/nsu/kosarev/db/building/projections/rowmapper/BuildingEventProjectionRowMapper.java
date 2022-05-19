package ru.nsu.kosarev.db.building.projections.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.projections.BuildingEventProjection;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingEventProjectionRowMapper implements RowMapper<BuildingEventProjection> {

    @Nullable
    @Override
    public BuildingEventProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BuildingEventProjection(
            rs.getInt("buildingId"),
            rs.getString("eventName"),
            rs.getString("eventType"),
            rs.getString("eventPlace"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("eventDate"))
        );
    }

}
