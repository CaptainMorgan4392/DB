package ru.nsu.kosarev.db.building.projection.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.projection.BuildingEventProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingEventProjectionRowMapper implements RowMapper<BuildingEventProjection> {

    @Nullable
    @Override
    public BuildingEventProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }

}
