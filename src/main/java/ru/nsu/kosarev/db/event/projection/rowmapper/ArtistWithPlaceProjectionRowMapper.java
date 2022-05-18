package ru.nsu.kosarev.db.event.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.event.projection.ArtistWithPlaceProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistWithPlaceProjectionRowMapper implements RowMapper<ArtistWithPlaceProjection> {

    @Nullable
    @Override
    public ArtistWithPlaceProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArtistWithPlaceProjection(
            rs.getString("artistName"),
            rs.getString("artistSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("artistBirthDate")),
            rs.getInt("artistPlace")
        );
    }

}
