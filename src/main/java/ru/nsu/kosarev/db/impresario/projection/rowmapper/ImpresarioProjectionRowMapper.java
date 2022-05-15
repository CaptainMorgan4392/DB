package ru.nsu.kosarev.db.impresario.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioProjection;
import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImpresarioProjectionRowMapper implements RowMapper<ImpresarioProjection> {

    @Nullable
    @Override
    public ImpresarioProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ImpresarioProjection(
            rs.getString("impresarioName"),
            rs.getString("impresarioSurname"),
            DateTimeFormatter.getFormattedDateFromTimestamp(rs.getDate("impresarioDate"))
        );
    }

}
