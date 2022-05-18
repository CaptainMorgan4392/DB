package ru.nsu.kosarev.db.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.nsu.kosarev.db.organizer.dto.OrganizerDTO;
import ru.nsu.kosarev.db.organizer.dto.OrganizerResponseDTO;
import ru.nsu.kosarev.db.organizer.projection.OrganizerEventProjection;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;
import ru.nsu.kosarev.db.organizer.repository.OrganizerJDBCRepository;
import ru.nsu.kosarev.db.organizer.repository.OrganizerRepository;
import ru.nsu.kosarev.db.organizer.sortingfilter.OrganizerSearchParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private OrganizerJDBCRepository organizerJDBCRepository;

    boolean isAlreadyExists(OrganizerDTO organizerDTO) {
        return organizerRepository.existsById(organizerDTO.getId());
    }

    OrganizerResponseDTO saveOrganizer(OrganizerDTO organizerDTO) {
        return OrganizerMapper.toOrganizerDTO(
            organizerRepository.save(
                OrganizerMapper.toOrganizer(organizerDTO)
            )
        );
    }

    Page<OrganizerResponseDTO> fetchOrganizersPage(OrganizerSearchParams organizerSearchParams) {
        Pageable sortedBySelectedField = organizerSearchParams.getSortBy() == null ?
            PageRequest.of(
                organizerSearchParams.getPageNumber(),
                organizerSearchParams.getPageSize()
            ) :
            PageRequest.of(
                organizerSearchParams.getPageNumber(),
                organizerSearchParams.getPageSize(),
                Sort.by(organizerSearchParams.getSortBy()).ascending()
            );

        Specification<Organizer> filter = buildSpec(organizerSearchParams);

        return organizerRepository.findAll(filter, sortedBySelectedField).map(OrganizerMapper::toOrganizerDTO);
    }

    List<OrganizerResponseDTO> fetchOrganizersList(OrganizerSearchParams organizerSearchParams) {
        Specification<Organizer> filter = buildSpec(organizerSearchParams);

        return organizerRepository.findAll(filter)
            .stream()
            .map(OrganizerMapper::toOrganizerDTO)
            .collect(Collectors.toList());
    }

    void deleteOrganizer(Integer organizerId) {
        organizerRepository.deleteById(organizerId);
    }

    public List<OrganizerWithEventCountProjection> getOrganizersWithEventCountsInPeriod(Date from, Date to) {
        return organizerJDBCRepository.getOrganizersWithEventCountsInPeriod(from, to);
    }

    public void bindOrganizerToEvent(Integer organizerId, Integer eventId) {
        organizerJDBCRepository.bindOrganizerToEvent(organizerId, eventId);
    }

    public List<OrganizerEventProjection> getEventsOfOrganizer(Integer organizerId) {
        return organizerJDBCRepository.getEventsOfOrganizer(organizerId);
    }

    public void deleteEventOfOrganizer(Integer organizerId, Integer eventId) {
        organizerJDBCRepository.deleteEventOfOrganizer(organizerId, eventId);
    }

    public List<OrganizerEventProjection> getEventsOfAllOrganizers() {
        return organizerJDBCRepository.getEventsOfAllOrganizers();
    }

    private Specification<Organizer> buildSpec(OrganizerSearchParams organizerSearchParams) {
        return new Specification<>() {
            @Nullable
            @Override
            public Predicate toPredicate(
                @NonNull Root<Organizer> root,
                @NonNull CriteriaQuery<?> query,
                @NonNull CriteriaBuilder criteriaBuilder
            ) {
                List<Predicate> filterPredicates = new ArrayList<>();

                if (organizerSearchParams.getName() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("name"), organizerSearchParams.getName()));
                }

                if (organizerSearchParams.getSurname() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("surname"), organizerSearchParams.getSurname()));
                }

                if (organizerSearchParams.getBirthDate() != null) {
                    filterPredicates.add(criteriaBuilder.equal(root.get("birthDate"), organizerSearchParams.getBirthDate()));
                }

                return criteriaBuilder.and(
                    filterPredicates.toArray(new Predicate[0])
                );
            }
        };
    }

}
