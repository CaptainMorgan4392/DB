package ru.nsu.kosarev.db.artist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.dto.ArtistsEventPlacesDTO;
import ru.nsu.kosarev.db.artist.projections.ArtistEventProjection;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.artist.projections.ArtistProjection;
import ru.nsu.kosarev.db.artist.projections.rowmappers.ArtistImpresarioJenreProjectionRowMapper;
import ru.nsu.kosarev.db.artist.projections.rowmappers.ArtistProjectionRowMapper;

import java.sql.PreparedStatement;
import java.util.Date;
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

    //Check for trigger working
    public void deleteArtistWithImpresarioInJenre(Integer impresarioId, Integer artistId, Integer jenreId) {
        jdbcTemplate.update(
            "DELETE FROM impresario_artist_jenre " +
                "WHERE impresario = ? AND artist = ? AND jenre = ?",
            impresarioId,
            artistId,
            jenreId
        );
    }

    //2
    public List<ArtistProjection> getArtistsInJenre(Integer jenreId) {
        return jdbcTemplate.query(
            "SELECT a.name AS artistName, " +
                "a.surname AS artistSurname, " +
                "a.birthDate AS artistDate " +
                "FROM artist a " +
                "INNER JOIN impresario_artist_jenre iaj on a.id = iaj.artist " +
                "WHERE iaj.jenre = ?",
            new ArtistProjectionRowMapper(),
            jenreId
        );
    }

    //10
    public List<ArtistProjection> getArtistsNotTakingPartInPeriod(Date from, Date to) {
        return jdbcTemplate.query(
            "SELECT foundArtistId, commonArtistId FROM " +
                "(SELECT x.id AS foundArtistId, y.id AS commonArtistId FROM " +
                "(SELECT a.id FROM artist a " +
                "INNER JOIN artist_event ae on a.id = ae.artist " +
                "INNER JOIN event e on e.id = ae.event " +
                "WHERE e.eventdate BETWEEN ? AND ?" +
                ") AS x " +
                "RIGHT JOIN LATERAL " +
                "(SELECT * FROM artist a) AS y ON x.id = y.id) AS lateralJoined " +
                "WHERE foundArtistId IS NULL",
            new ArtistProjectionRowMapper(),
            from,
            to
        );
    }

    public void bindArtistToEvent(Integer artistId, Integer eventId) {
        //TODO
    }

    public List<ArtistEventProjection> getEventsOfArtist(Integer artistId) {
        //TODO
        return null;
    }

    public void deleteEventOfArtist(Integer artistId, Integer eventId) {
        //TODO
    }

    public void bindArtistsToPlacesInEvent(ArtistsEventPlacesDTO artistsEventPlacesDTO) {
        //TODO
    }

}
