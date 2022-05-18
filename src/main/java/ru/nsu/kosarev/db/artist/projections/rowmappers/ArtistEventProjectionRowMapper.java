package ru.nsu.kosarev.db.artist.projections.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.artist.projections.ArtistEventProjection;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistEventProjectionRowMapper implements RowMapper<ArtistEventProjection> {

    @Nullable
    @Override
    public ArtistEventProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArtistEventProjection(
            rs.getString("artistName"),
            rs.getString("artistSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("artistDate")),
            rs.getString("eventName"),
            rs.getString("eventType"),
            rs.getString("eventPlace"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("eventDate"))
        );
    }

}
