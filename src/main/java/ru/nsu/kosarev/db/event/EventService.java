package ru.nsu.kosarev.db.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.nsu.kosarev.db.building.repository.BuildingRepository;
import ru.nsu.kosarev.db.event.dto.EventDTO;
import ru.nsu.kosarev.db.event.dto.EventResponseDTO;
import ru.nsu.kosarev.db.event.repository.EventRepository;
import ru.nsu.kosarev.db.event.sortingfilter.EventSearchParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    private final Function<EventResponseDTO, EventResponseDTO> transformEventPlace = eventResponseDTO -> {
        buildingRepository.findById(Integer.parseInt(eventResponseDTO.getEventPlace()))
            .ifPresent(value -> eventResponseDTO.setEventPlace(value.getName()));

        return eventResponseDTO;
    };

    boolean isAlreadyExists(EventDTO eventDTO) {
        return eventRepository.existsById(eventDTO.getId());
    }

    EventResponseDTO saveEvent(EventDTO eventDTO) {
        return transformEventPlace.apply(
            EventMapper.toEventDto(
                eventRepository.save(
                    EventMapper.toEvent(eventDTO)
                )
            )
        );
    }

    Page<EventResponseDTO> fetchEventsPage(EventSearchParams eventSearchParams) {
        Pageable sortedBySelectedField = eventSearchParams.getSortBy() == null ?
            PageRequest.of(
                eventSearchParams.getPageNumber(),
                eventSearchParams.getPageSize()
            ) :
            PageRequest.of(
                eventSearchParams.getPageNumber(),
                eventSearchParams.getPageSize(),
                Sort.by(eventSearchParams.getSortBy()).ascending()
            );

        Specification<Event> filter = buildSpec(eventSearchParams);

        return eventRepository.findAll(filter, sortedBySelectedField)
            .map(EventMapper::toEventDto)
            .map(transformEventPlace);
    }

    List<EventResponseDTO> fetchEventsList(EventSearchParams eventSearchParams) {
        Specification<Event> filter = buildSpec(eventSearchParams);

        return eventRepository.findAll(filter)
            .stream()
            .map(EventMapper::toEventDto)
            .map(transformEventPlace)
            .collect(Collectors.toList());
    }

    void deleteEvent(Integer artistId) {
        eventRepository.deleteById(artistId);
    }

    private Specification<Event> buildSpec(EventSearchParams eventSearchParams) {
        return new Specification<>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                @NonNull Root<Event> root,
                @NonNull CriteriaQuery<?> query,
                @NonNull CriteriaBuilder criteriaBuilder
            ) {
                List<Predicate> filterPredicates = new ArrayList<>();

                if (eventSearchParams.getName() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("name"), eventSearchParams.getName()));
                }

                if (eventSearchParams.getEventType() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("surname"), eventSearchParams.getEventType()));
                }

                if (eventSearchParams.getEventPlace() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("eventPlace"), eventSearchParams.getEventPlace()));
                }

                if (eventSearchParams.getEventDate() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("eventDate"), eventSearchParams.getEventDate()));
                }

                return criteriaBuilder.and(
                    filterPredicates.toArray(new Predicate[0])
                );
            }
        };
    }

}
