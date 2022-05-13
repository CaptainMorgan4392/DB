package ru.nsu.kosarev.db.artist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.artist.projections.rowmappers.ArtistImpresarioJenreProjectionRowMapper;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ArtistJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertArtistWithImpresarioInJenre(ArtistImpresarioJenreDto artistImpresarioJenreDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (?, ?, ?)"
            );

            preparedStatement.setInt(1, artistImpresarioJenreDto.getImpresarioId());
            preparedStatement.setInt(2, artistImpresarioJenreDto.getArtistId());
            preparedStatement.setInt(3, artistImpresarioJenreDto.getJenreId());

            return preparedStatement;
        }, keyHolder);
    }

    public List<ArtistImpresarioJenreProjection> getArtistsWithImpresariosInJenre() {
        return jdbcTemplate.query(
            "SELECT a.name AS artistName, " +
                "a.surname AS artistSurname, " +
                "a.birthDate AS artistDate, " +
                "i.name AS impresarioName, " +
                "i.surname AS impresarioSurname, " +
                "i.birthDate AS impresarioDate, " +
                "j.name AS jenreName " +
                "FROM impresario_artist_jenre iaj " +
                "INNER JOIN artist a on iaj.artist = a.id " +
                "INNER JOIN impresario i on i.id = iaj.impresario " +
                "INNER JOIN jenre j on j.id = iaj.jenre ",
            new ArtistImpresarioJenreProjectionRowMapper()
        );
    }

}
