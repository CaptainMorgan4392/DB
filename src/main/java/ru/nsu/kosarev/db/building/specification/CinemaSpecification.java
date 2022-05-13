package ru.nsu.kosarev.db.building.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.entity.Cinema;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CinemaSpecification implements Specification<Cinema> {

    private final List<Integer> ids;

    private final BuildingSearchParams buildingSearchParams;

    public CinemaSpecification(List<Integer> ids, BuildingSearchParams buildingSearchParams) {
        this.ids = ids;
        this.buildingSearchParams = buildingSearchParams;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Cinema> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> filterPredicates = new ArrayList<>();

        if (buildingSearchParams.getDiagonal() != null) {
            filterPredicates.add(
                criteriaBuilder.equal(
                    root.get("diagonal"),
                    buildingSearchParams.getDiagonal()
                )
            );
        }

        if (buildingSearchParams.getAddress() != null) {
            filterPredicates.add(
                criteriaBuilder.equal(
                    root.get("address"),
                    buildingSearchParams.getAddress()
                )
            );
        }

        filterPredicates.add(root.get("id").in(ids));

        return criteriaBuilder.and(
            filterPredicates.toArray(new Predicate[0])
        );
    }

}
