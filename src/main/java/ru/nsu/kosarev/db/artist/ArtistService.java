package ru.nsu.kosarev.db.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.dto.ArtistResponseDTO;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.artist.projections.ArtistProjection;
import ru.nsu.kosarev.db.artist.projections.rowmappers.ArtistProjectionRowMapper;
import ru.nsu.kosarev.db.artist.repository.ArtistJDBCRepository;
import ru.nsu.kosarev.db.artist.repository.ArtistRepository;
import ru.nsu.kosarev.db.artist.sortingfilter.ArtistSearchParams;
import ru.nsu.kosarev.db.common.utils.MyOwnTransactionManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistJDBCRepository artistJDBCRepository;

    @Autowired
    private MyOwnTransactionManager myOwnTransactionManager;

    boolean isAlreadyExists(ArtistDTO artistDTO) {
        return artistRepository.existsById(artistDTO.getId());
    }

    ArtistResponseDTO saveArtist(ArtistDTO artistDTO) {
        return myOwnTransactionManager.transaction(() -> ArtistMapper.toArtistDto(
            artistRepository.save(
                ArtistMapper.toArtist(artistDTO)
            )
        ));
    }

    Page<ArtistResponseDTO> fetchArtistsPage(ArtistSearchParams artistSearchParams) {
        Pageable sortedBySelectedField = artistSearchParams.getSortBy() == null ?
            PageRequest.of(
                artistSearchParams.getPageNumber(),
                artistSearchParams.getPageSize()
            ) :
            PageRequest.of(
                artistSearchParams.getPageNumber(),
                artistSearchParams.getPageSize(),
                Sort.by(artistSearchParams.getSortBy()).ascending()
            );

        Specification<Artist> filter = buildSpec(artistSearchParams);

        return artistRepository.findAll(filter, sortedBySelectedField).map(ArtistMapper::toArtistDto);
    }

    List<ArtistResponseDTO> fetchArtistsList(ArtistSearchParams artistSearchParams) {
        Specification<Artist> filter = buildSpec(artistSearchParams);

        return artistRepository.findAll(filter)
            .stream()
            .map(ArtistMapper::toArtistDto)
            .collect(Collectors.toList());
    }

    @Transactional
    void deleteArtist(Integer artistId) {
        artistRepository.deleteById(artistId);
    }

    @Transactional
    public void makeArtistWorkingWithImpresarioInJenre(ArtistImpresarioJenreDto artistImpresarioJenreDto) {
        artistJDBCRepository.insertArtistWithImpresarioInJenre(artistImpresarioJenreDto);
    }

    public List<ArtistImpresarioJenreProjection> getArtistWorkingWithImpresarioInJenre() {
        return artistJDBCRepository.getArtistsWithImpresariosInJenre();
    }

    public void updateArtistWithImpresarioInJenre(ArtistImpresarioJenreDto artistImpresarioJenreDto) {
        artistJDBCRepository.updateArtistWithImpresarioInJenre(artistImpresarioJenreDto);
    }

    public void deleteArtistWithImpresarioInJenre(Integer artistId) {
        artistJDBCRepository.deleteArtistWithImpresarioInJenre(artistId);
    }

    public List<ArtistProjection> getArtistsInJenre(Integer jenreId) {
        return artistJDBCRepository.getArtistsInJenre(jenreId);
    }

    public List<ArtistProjection> getArtistsNotTakingPartInPeriod(Date from, Date to) {
        return artistJDBCRepository.getArtistsNotTakingPartInPeriod(from, to);
    }

    private Specification<Artist> buildSpec(ArtistSearchParams artistSearchParams) {
         return new Specification<>() {
             @Nullable
             @Override
             public Predicate toPredicate(
                 @NonNull Root<Artist> root,
                 @NonNull CriteriaQuery<?> query,
                 @NonNull CriteriaBuilder criteriaBuilder
             ) {
                 List<Predicate> filterPredicates = new ArrayList<>();

                 if (artistSearchParams.getName() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("name"), artistSearchParams.getName()));
                 }

                 if (artistSearchParams.getSurname() != null) {
                     filterPredicates.add(criteriaBuilder.equal(root.get("surname"), artistSearchParams.getSurname()));
                 }

                 if (artistSearchParams.getBirthDate() != null) {
                     filterPredicates.add(criteriaBuilder.equal(root.get("birthDate"), artistSearchParams.getBirthDate()));
                 }

                 return criteriaBuilder.and(
                     filterPredicates.toArray(new Predicate[0])
                 );
             }
         };
    }

}
