package ru.nsu.kosarev.db.artist.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.artist.Artist;

import java.util.List;

@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Integer>, JpaSpecificationExecutor<Artist> {
    List<Artist> findAll();
}
