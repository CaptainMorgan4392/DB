package ru.nsu.kosarev.db.impresario.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.impresario.projections.ImpresarioArtistProjection;
import ru.nsu.kosarev.db.impresario.projections.rowmapper.ImpresarioArtistProjectionRowMapper;

import java.util.List;

@Repository
public class ImpresarioJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ImpresarioArtistProjection> getArtistsWithImpresarioInMultipleJenres(Integer impresarioId) {
        return jdbcTemplate.query(
            "SELECT artistName, artistSurname, artistDate, impresarioName, impresarioSurname, impresarioDate " +
                "FROM " +
                "(SELECT a.id, a.name AS artistName, " +
                "a.surname AS artistSurname, " +
                "a.birthDate AS artistDate, " +
                "i.id, i.name AS impresarioName, " +
                "i.surname AS impresarioSurname, " +
                "i.birthDate AS impresarioDate " +
                "FROM impresario i " +
                "INNER JOIN impresario_artist_jenre iaj on i.id = iaj.impresario " +
                "INNER JOIN artist a on a.id = iaj.artist " +
                "INNER JOIN jenre j on j.id = iaj.jenre " +
                "WHERE i.id = ? " +
                "GROUP BY (a.id, a.name, a.surname, a.birthDate, " +
                "i.id, i.name, i.surname, i.birthDate) HAVING count(*) > 1) AS X " +
                "ORDER BY (artistName, artistSurname)",
            new ImpresarioArtistProjectionRowMapper(),
            impresarioId
        );
    }

    public List<ImpresarioArtistProjection> getArtistsOfAllImpresarios() {
        return jdbcTemplate.query(
            "SELECT i.id AS impresarioId, " +
                "i.name AS impresarioName, " +
                "i.surname AS impresarioSurname, " +
                "i.birthDate AS impresarioDate, " +
                "a.id AS artistId, " +
                "a.name AS artistName, " +
                "a.surname AS artistSurname, " +
                "a.birthDate AS artistDate " +
                "FROM impresario i " +
                "LEFT JOIN (impresario_artist_jenre iaj " +
                "INNER JOIN artist a on a.id = iaj.artist " +
                "INNER JOIN jenre j on j.id = iaj.jenre) on i.id = iaj.impresario" ,
            new ImpresarioArtistProjectionRowMapper()
        );
    }

}
