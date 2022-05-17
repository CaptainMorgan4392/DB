package ru.nsu.kosarev.db.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.organizer.dto.OrganizerDTO;
import ru.nsu.kosarev.db.organizer.dto.OrganizerResponseDTO;
import ru.nsu.kosarev.db.organizer.dto.OrganizerWithEventsDTO;
import ru.nsu.kosarev.db.organizer.dto.PeriodDTO;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;
import ru.nsu.kosarev.db.organizer.sortingfilter.OrganizerSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/organizer")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping(value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganizerResponseDTO createOrganizer(@RequestBody OrganizerDTO organizerDTO) {
        if (organizerDTO.getId() != null && organizerService.isAlreadyExists(organizerDTO)) {
            return null;
        }

        return organizerService.saveOrganizer(organizerDTO);
    }

    @PostMapping(value = "/fetch/page",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrganizerResponseDTO> fetchOrganizersPage(@RequestBody OrganizerSearchParams organizerSearchParams) {
        if (organizerSearchParams.getPageNumber() == null || organizerSearchParams.getPageSize() == null) {
            return null;
        }

        return organizerService.fetchOrganizersPage(organizerSearchParams).getContent();
    }

    @PostMapping(value = "/fetch/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrganizerResponseDTO> fetchOrganizersList(@RequestBody OrganizerSearchParams organizerSearchParams) {
        return organizerService.fetchOrganizersList(organizerSearchParams);
    }

    @PostMapping(value = "/update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganizerResponseDTO updateOrganizer(@RequestBody OrganizerDTO organizerDTO) {
        if (organizerDTO.getId() == null || !organizerService.isAlreadyExists(organizerDTO)) {
            return null;
        }

        return organizerService.saveOrganizer(organizerDTO);
    }

    @PostMapping(value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrganizer(@PathVariable("id") Integer organizerId) {
        organizerService.deleteOrganizer(organizerId);
    }

    @PostMapping(value = "/getOrganizersWithEventCountsInPeriod")
    public List<OrganizerWithEventCountProjection> getOrganizersWithEventCountsInPeriod(
        @RequestBody PeriodDTO periodDTO
    ) {
        return organizerService.getOrganizersWithEventCountsInPeriod(
            periodDTO.getFrom(),
            periodDTO.getTo()
        );
    }

    @PostMapping(value = "/organizerEvent/{org_id}/{event_id}")
    public void bindOrganizerToEvent(
        @PathVariable("org_id") Integer organizerId,
        @PathVariable("event_id") Integer eventId
    ) {
        organizerService.bindOrganizerToEvent(organizerId, eventId);
    }

    @GetMapping(value = "/organizerEvent/get/{id}")
    public List<OrganizerWithEventsDTO> getEventsOfOrganizer(@PathVariable("id") Integer organizerId) {
        return organizerService.getEventsOfOrganizer(organizerId);
    }

    @DeleteMapping(value = "/organizerEvent/delete/{org_id}/{event_id}")
    public void deleteEventOfOrganizer(
        @PathVariable("org_id") Integer organizerId,
        @PathVariable("event_id") Integer eventId
    ) {
        organizerService.deleteEventOfOrganizer(organizerId, eventId);
    }

}
