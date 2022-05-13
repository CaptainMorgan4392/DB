package ru.nsu.kosarev.db.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.kosarev.db.building.entity.Stage;

public interface StageRepository extends JpaRepository<Stage, Integer>, JpaSpecificationExecutor<Stage> {
}
