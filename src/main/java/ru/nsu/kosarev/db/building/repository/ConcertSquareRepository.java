package ru.nsu.kosarev.db.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.kosarev.db.building.entity.ConcertSquare;

public interface ConcertSquareRepository extends JpaRepository<ConcertSquare, Integer>, JpaSpecificationExecutor<ConcertSquare> {
}
