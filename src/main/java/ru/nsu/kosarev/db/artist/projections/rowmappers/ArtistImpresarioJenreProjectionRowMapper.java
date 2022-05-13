package ru.nsu.kosarev.db.artist.projections.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistImpresarioJenreProjectionRowMapper implements RowMapper<ArtistImpresarioJenreProjection> {

    @Nullable
    @Override
    public ArtistImpresarioJenreProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArtistImpresarioJenreProjection(
            rs.getString("artistName"),
            rs.getString("artistSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("artistDate")),
            rs.getString("impresarioName"),
            rs.getString("impresarioSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("impresarioDate")),
            rs.getString("jenreName")
        );
    }

}
