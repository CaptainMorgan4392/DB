package ru.nsu.kosarev.db.building.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BuildingJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //12
    //TODO

}
