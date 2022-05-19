package ru.nsu.kosarev.db.building.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.kosarev.db.building.projections.BuildingEventProjection;
import ru.nsu.kosarev.db.building.projections.rowmapper.BuildingEventProjectionRowMapper;

import java.util.Collections;
import java.util.List;

@Repository
public class BuildingJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //12
    public List<BuildingEventProjection> getEventsInBuildings(List<Integer> buildingIds) {
        String inSql = String.join(",", Collections.nCopies(buildingIds.size(), "?"));

        return jdbcTemplate.query(
            String.format(
                "SELECT e.eventplace as buildingId, " +
                    "e.name as eventName, " +
                    "et.eventtype as eventType, " +
                    "b.name as eventPlace, " +
                    "e.eventdate as eventDate " +
                    "FROM event e " +
                    "INNER JOIN event_type et on et.id = e.eventtype " +
                    "INNER JOIN building b on b.id = e.eventplace " +
                    "WHERE e.eventplace IN (%s) " +
                    "ORDER BY (b.id, e.id)",
                inSql
            ),
            new BuildingEventProjectionRowMapper(),
            buildingIds.toArray()
        );
    }

    public Integer fetchEventPlaceByEventId(@PathVariable Integer eventId) {
        return jdbcTemplate.queryForObject(
            "SELECT e.eventplace " +
                "FROM event e WHERE e.id = ?",
            Integer.class,
            eventId
        );
    }

}
