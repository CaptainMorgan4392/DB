package ru.nsu.kosarev.db.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.kosarev.db.building.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Integer>, JpaSpecificationExecutor<Cinema> {
}
