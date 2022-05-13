package ru.nsu.kosarev.db.organizer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.organizer.Organizer;

import java.util.Date;
import java.util.List;

@Repository
public interface OrganizerJDBCRepository extends CrudRepository<Organizer, Integer> {

    //11
    @Query(value = "SELECT DISTINCT x.organizer_id, x.name, x.surname, x.birthdate, " +
        "count(x.event_id) OVER (PARTITION BY x.organizer_id) " +
        "FROM " +
        "(SELECT o.id as organizer_id, o.name, o.surname, o.birthdate, e.id as event_id " +
        "FROM organizer o INNER JOIN organizer_event oe on o.id = oe.organizer " +
        "INNER JOIN event e on e.id = oe.event " +
        "WHERE e.eventdate BETWEEN :from AND :to" +
        ") x", nativeQuery = true)
    List<Organizer> getOrganizersAndCountOfEventsInPeriod(@Param("from") Date dateFrom, @Param("to") Date dateTo);



}
