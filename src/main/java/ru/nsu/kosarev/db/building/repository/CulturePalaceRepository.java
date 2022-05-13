package ru.nsu.kosarev.db.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.kosarev.db.building.entity.CulturePalace;

public interface CulturePalaceRepository extends JpaRepository<CulturePalace, Integer>, JpaSpecificationExecutor<CulturePalace> {
}
