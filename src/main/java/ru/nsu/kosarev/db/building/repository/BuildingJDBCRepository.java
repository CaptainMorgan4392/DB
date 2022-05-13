package ru.nsu.kosarev.db.building.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.building.entity.Building;

@Repository
public interface BuildingJDBCRepository extends CrudRepository<Building, Integer> {

    //12. building + event

}
