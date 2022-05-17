package ru.nsu.kosarev.db.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.event.dto.EventDTO;
import ru.nsu.kosarev.db.event.dto.EventResponseDTO;
import ru.nsu.kosarev.db.event.dto.PeriodOrganizerDTO;
import ru.nsu.kosarev.db.event.projection.ArtistWithPlaceProjection;
import ru.nsu.kosarev.db.event.projection.EventProjection;
import ru.nsu.kosarev.db.event.sortingfilter.EventSearchParams;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventResponseDTO createEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO.getId() != null && eventService.isAlreadyExists(eventDTO)) {
            return null;
        }

        return eventService.saveEvent(eventDTO);
    }

    @PostMapping(value = "/fetch/page",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventResponseDTO> fetchEventsPage(@RequestBody EventSearchParams eventSearchParams) {
        if (eventSearchParams.getPageNumber() == null || eventSearchParams.getPageSize() == null) {
            return null;
        }

        return eventService.fetchEventsPage(eventSearchParams).getContent();
    }

    @PostMapping(value = "/fetch/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventResponseDTO> fetchEventsList(@RequestBody EventSearchParams eventSearchParams) {
        return eventService.fetchEventsList(eventSearchParams);
    }

    @PostMapping(value = "/update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventResponseDTO updateEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO.getId() == null || !eventService.isAlreadyExists(eventDTO)) {
            return null;
        }

        return eventService.saveEvent(eventDTO);
    }

    @PostMapping(value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEvent(@PathVariable("id") Integer eventId) {
        eventService.deleteEvent(eventId);
    }

    @PostMapping(value = "/getEventsInPeriodOrByOrganizer")
    public List<EventProjection> getEventsInPeriodOrByOrganizer(@RequestBody PeriodOrganizerDTO periodOrganizerDTO) {
        return eventService.getEventsInPeriodOrByOrganizer(
            periodOrganizerDTO.getFrom(),
            periodOrganizerDTO.getTo(),
            periodOrganizerDTO.getId()
        );
    }

    @GetMapping(value = "/getArtistsWithPlaces/{id}")
    public List<ArtistWithPlaceProjection> getArtistsWithPlaces(@PathVariable("id") Integer eventId) {
        return eventService.getArtistsWithPlaces(eventId);
    }

    @GetMapping(value = "/getEventsInBuilding/{id}")
    public List<EventProjection> getEventsInBuilding(@PathVariable("id") Integer buildingId) {
        return eventService.getEventsInBuilding(buildingId);
    }

}
