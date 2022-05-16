package ru.nsu.kosarev.db.artist.projections.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.artist.projections.ArtistsTogetherProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistsTogetherProjectionRowMapper implements RowMapper<ArtistsTogetherProjection> {

    @Nullable
    @Override
    public ArtistsTogetherProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }

}
