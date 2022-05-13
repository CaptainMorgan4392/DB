package ru.nsu.kosarev.db.event.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.event.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventJDBCRepository extends CrudRepository<Event, Integer> {

    //6
    @Query(value = "SELECT e.* " +
        "FROM event e " +
        "INNER JOIN organizer_event oe on e.id = oe.event " +
        "INNER JOIN event_type et on et.id = e.eventtype " +
        "WHERE et.eventtype = :eventType " +
        "OR oe.organizer = :id " +
        "OR e.eventdate BETWEEN :from AND :to", nativeQuery = true)
    List<Event> getEventsInPeriodOrByOrganizer(
        @Param("from") Date dateFrom,
        @Param("to") Date dateTo,
        @Param("eventType") String eventType,
        @Param("id") Integer organizerId
    );

    //8
    @Query(value = "SELECT e.* " +
        "FROM event e " +
        "INNER JOIN building b on b.id = e.eventplace " +
        "INNER JOIN event_type et on et.id = e.eventtype " +
        "WHERE b.id = :id", nativeQuery = true)
    List<Event> getEventsInBuilding(@Param("id") Integer buildingId);

}
