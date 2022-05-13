package ru.nsu.kosarev.db.building.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.nsu.kosarev.db.building.entity.ConcertSquare;
import ru.nsu.kosarev.db.building.entity.CulturePalace;
import ru.nsu.kosarev.db.building.entity.Theatre;
import ru.nsu.kosarev.db.building.sortingfilter.BuildingSearchParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CulturePalaceSpecification implements Specification<CulturePalace> {

    private final List<Integer> ids;

    private final BuildingSearchParams buildingSearchParams;

    public CulturePalaceSpecification(List<Integer> ids, BuildingSearchParams buildingSearchParams) {
        this.ids = ids;
        this.buildingSearchParams = buildingSearchParams;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<CulturePalace> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> filterPredicates = new ArrayList<>();

        if (buildingSearchParams.getDiagonal() != null) {
            filterPredicates.add(criteriaBuilder.or());
        }

        if (buildingSearchParams.getAddress() != null) {
            filterPredicates.add(
                criteriaBuilder.like(
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
