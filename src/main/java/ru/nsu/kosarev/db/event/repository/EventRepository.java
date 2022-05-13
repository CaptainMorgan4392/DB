package ru.nsu.kosarev.db.event.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.kosarev.db.event.Event;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer>, JpaSpecificationExecutor<Event> {



}
