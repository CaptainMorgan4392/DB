package ru.nsu.kosarev.db.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.event.dto.EventDTO;
import ru.nsu.kosarev.db.event.dto.EventResponseDTO;
import ru.nsu.kosarev.db.event.sortingfilter.EventSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PutMapping(value = "/add")
    public EventResponseDTO createEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO.getId() != null && eventService.isAlreadyExists(eventDTO)) {
            return null;
        }

        return eventService.saveEvent(eventDTO);
    }

    @GetMapping(value = "/fetch/page")
    public List<EventResponseDTO> fetchEventsPage(@RequestBody EventSearchParams eventSearchParams) {
        if (eventSearchParams.getPageNumber() == null || eventSearchParams.getPageSize() == null) {
            return null;
        }

        return eventService.fetchEventsPage(eventSearchParams).getContent();
    }

    @GetMapping(value = "/fetch/list")
    public List<EventResponseDTO> fetchEventsList(@RequestBody EventSearchParams eventSearchParams) {
        return eventService.fetchEventsList(eventSearchParams);
    }

    @PatchMapping(value = "/update")
    public EventResponseDTO updateEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO.getId() == null || !eventService.isAlreadyExists(eventDTO)) {
            return null;
        }

        return eventService.saveEvent(eventDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteEvent(@PathVariable("id") Integer eventId) {
        eventService.deleteEvent(eventId);
    }

}
