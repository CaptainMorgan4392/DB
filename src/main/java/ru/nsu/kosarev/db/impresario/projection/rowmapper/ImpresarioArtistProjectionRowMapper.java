package ru.nsu.kosarev.db.impresario.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioArtistProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImpresarioArtistProjectionRowMapper implements RowMapper<ImpresarioArtistProjection> {

    @Nullable
    @Override
    public ImpresarioArtistProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ImpresarioArtistProjection(
            rs.getInt("impresarioId"),
            rs.getString("impresarioName"),
            rs.getString("impresarioSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("impresarioDate")),
            rs.getInt("artistId"),
            rs.getString("artistName"),
            rs.getString("artistSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("artistDate"))
        );
    }

}
