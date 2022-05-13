package ru.nsu.kosarev.db.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.kosarev.db.building.dto.BuildingDTO;
import ru.nsu.kosarev.db.building.dto.BuildingResponseDTO;
import ru.nsu.kosarev.db.building.entity.Building;
import ru.nsu.kosarev.db.building.entity.Cinema;
import ru.nsu.kosarev.db.building.entity.ConcertSquare;
import ru.nsu.kosarev.db.building.entity.CulturePalace;
import ru.nsu.kosarev.db.building.entity.Stage;
import ru.nsu.kosarev.db.building.entity.Theatre;
import ru.nsu.kosarev.db.building.repository.BuildingRepository;
import ru.nsu.kosarev.db.building.repository.CinemaRepository;
import ru.nsu.kosarev.db.building.repository.ConcertSquareRepository;
import ru.nsu.kosarev.db.building.repository.CulturePalaceRepository;
import ru.nsu.kosarev.db.building.repository.StageRepository;
import ru.nsu.kosarev.db.building.repository.TheatreRepository;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;
import ru.nsu.kosarev.db.building.specification.CinemaSpecification;
import ru.nsu.kosarev.db.building.specification.ConcertSquareSpecification;
import ru.nsu.kosarev.db.building.specification.CulturePalaceSpecification;
import ru.nsu.kosarev.db.building.specification.StageSpecification;
import ru.nsu.kosarev.db.building.specification.TheatreSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CulturePalaceRepository culturePalaceRepository;

    @Autowired
    private ConcertSquareRepository concertSquareRepository;

    private final Map<BuildingType, Function<BuildingDTO, BuildingResponseDTO>> savingForConcreteType = Map.of(
        BuildingType.THEATRE, (buildingDTO -> {
            Pair<Building, Theatre> pair = BuildingMapper.decomposeTheatre(buildingDTO);

            Building building = buildingRepository.save(pair.getFirst());

            pair.getSecond().setId(building.getId());
            Theatre theatre = theatreRepository.save(pair.getSecond());

            return BuildingMapper.compose(building, theatre);
        }) ,
        BuildingType.STAGE, (buildingDTO -> {
            Pair<Building, Stage> pair = BuildingMapper.decomposeStage(buildingDTO);

            Building building = buildingRepository.save(pair.getFirst());

            pair.getSecond().setId(building.getId());
            Stage stage = stageRepository.save(pair.getSecond());

            return BuildingMapper.compose(building, stage);
        }) ,
        BuildingType.CINEMA, (buildingDTO -> {
            Pair<Building, Cinema> pair = BuildingMapper.decomposeCinema(buildingDTO);

            Building building = buildingRepository.save(pair.getFirst());

            pair.getSecond().setId(building.getId());
            Cinema cinema = cinemaRepository.save(pair.getSecond());

            return BuildingMapper.compose(building, cinema);
        }) ,
        BuildingType.CONCERT_SQUARE, (buildingDTO -> {
            Pair<Building, ConcertSquare> pair = BuildingMapper.decomposeConcertSquare(buildingDTO);

            Building building = buildingRepository.save(pair.getFirst());

            pair.getSecond().setId(building.getId());
            ConcertSquare concertSquare = concertSquareRepository.save(pair.getSecond());

            return BuildingMapper.compose(building, concertSquare);
        }) ,
        BuildingType.CULTURE_PALACE, (buildingDTO -> {
            Pair<Building, CulturePalace> pair = BuildingMapper.decomposeCulturePalace(buildingDTO);

            Building building = buildingRepository.save(pair.getFirst());

            pair.getSecond().setId(building.getId());
            CulturePalace culturePalace = culturePalaceRepository.save(pair.getSecond());

            return BuildingMapper.compose(building, culturePalace);
        })
    );

    boolean isAlreadyExists(BuildingDTO buildingDTO) {
        return buildingRepository.existsById(buildingDTO.getId());
    }

    @Transactional
    BuildingResponseDTO saveBuilding(BuildingDTO buildingDTO) {
        return savingForConcreteType.get(buildingDTO.getBuildingType()).apply(buildingDTO);
    }

    List<BuildingResponseDTO> fetchBuildingsPage(BuildingSearchParams buildingSearchParams) {
        List<BuildingResponseDTO> buildingResponseDTOS = fetchBuildingsList(buildingSearchParams);

        return fetchPageFromCommonList(
            buildingResponseDTOS,
            buildingSearchParams.getPageNumber(),
            buildingSearchParams.getPageSize()
        );
    }

    List<BuildingResponseDTO> fetchBuildingsList(BuildingSearchParams buildingSearchParams) {
        List<Building> fetchedByCommonParams = new ArrayList<>(
            fetchAbstractBuildingsByCommonParams(buildingSearchParams)
        );

        List<Integer> ids = fetchedByCommonParams.stream()
            .map(Building::getId)
            .collect(Collectors.toList());

        List<BuildingResponseDTO> theatres = theatreRepository.findAll(new TheatreSpecification(ids, buildingSearchParams))
            .stream()
            .map(
                theatre -> BuildingMapper.compose(
                    fetchedByCommonParams.stream()
                        .filter(building -> building.getId().equals(theatre.getId()))
                        .collect(Collectors.toList()).get(0),
                    theatre
                )
            ).collect(Collectors.toList());

        List<BuildingResponseDTO> stages = stageRepository.findAll(new StageSpecification(ids, buildingSearchParams))
            .stream()
            .map(
                stage -> BuildingMapper.compose(
                    fetchedByCommonParams.stream()
                        .filter(building -> building.getId().equals(stage.getId()))
                        .collect(Collectors.toList()).get(0),
                    stage
                )
            ).collect(Collectors.toList());

        List<BuildingResponseDTO> cinemas = cinemaRepository.findAll(new CinemaSpecification(ids, buildingSearchParams))
            .stream()
            .map(
                stage -> BuildingMapper.compose(
                    fetchedByCommonParams.stream()
                        .filter(building -> building.getId().equals(stage.getId()))
                        .collect(Collectors.toList()).get(0),
                    stage
                )
            ).collect(Collectors.toList());

        List<BuildingResponseDTO> concertSquares = concertSquareRepository.findAll(new ConcertSquareSpecification(ids, buildingSearchParams))
            .stream()
            .map(
                stage -> BuildingMapper.compose(
                    fetchedByCommonParams.stream()
                        .filter(building -> building.getId().equals(stage.getId()))
                        .collect(Collectors.toList()).get(0),
                    stage
                )
            ).collect(Collectors.toList());

        List<BuildingResponseDTO> culturePalaces = culturePalaceRepository.findAll(new CulturePalaceSpecification(ids, buildingSearchParams))
            .stream()
            .map(
                stage -> BuildingMapper.compose(
                    fetchedByCommonParams.stream()
                        .filter(building -> building.getId().equals(stage.getId()))
                        .collect(Collectors.toList()).get(0),
                    stage
                )
            ).collect(Collectors.toList());

        return Stream.of(
            theatres.stream(),
            cinemas.stream(),
            concertSquares.stream(),
            culturePalaces.stream(),
            stages.stream()
        ).flatMap(i -> i).collect(Collectors.toList());
    }

    @Transactional
    void deleteBuilding(Integer buildingId) {
        buildingRepository.deleteById(buildingId);
    }

    private List<BuildingResponseDTO> fetchPageFromCommonList(
        List<BuildingResponseDTO> buildingResponseDTOS,
        int pageNumber,
        int pageSize
    ) {
        int startIndex = pageSize * pageNumber;
        int endIndex = pageSize * (pageNumber + 1) - 1;

        return IntStream.range(startIndex, endIndex)
            .mapToObj(i -> i < buildingResponseDTOS.size() ? buildingResponseDTOS.get(i) : null)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private List<Building> fetchAbstractBuildingsByCommonParams(BuildingSearchParams buildingSearchParams) {
        Specification<Building> abstractFilter = buildCommonSpec(buildingSearchParams);

        return buildingRepository.findAll(abstractFilter);
    }

    private Specification<Building> buildCommonSpec(BuildingSearchParams buildingSearchParams) {
        return new Specification<>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Building> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> filterPredicates = new ArrayList<>();

                if (buildingSearchParams.getName() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("name"), buildingSearchParams.getName()));
                }

                if (buildingSearchParams.getCapacity() != null) {
                    filterPredicates.add(
                        criteriaBuilder.equal(
                            root.get("capacity"),
                            buildingSearchParams.getCapacity()
                        )
                    );
                }

                if (buildingSearchParams.getBuildingType() != null) {
                    filterPredicates.add(
                        criteriaBuilder.equal(
                            root.get("buildingType"),
                            buildingSearchParams.getBuildingType().name()
                        )
                    );
                }

                return criteriaBuilder.and(
                    filterPredicates.toArray(new Predicate[0])
                );
            }
        };
    }



}
