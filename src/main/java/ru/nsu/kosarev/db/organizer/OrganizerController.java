package ru.nsu.kosarev.db.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.organizer.dto.OrganizerDTO;
import ru.nsu.kosarev.db.organizer.dto.OrganizerResponseDTO;
import ru.nsu.kosarev.db.organizer.dto.PeriodDTO;
import ru.nsu.kosarev.db.organizer.projection.OrganizerWithEventCountProjection;
import ru.nsu.kosarev.db.organizer.sortingfilter.OrganizerSearchParams;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/organizer")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PutMapping(value = "/add")
    public OrganizerResponseDTO createOrganizer(@RequestBody OrganizerDTO organizerDTO) {
        if (organizerDTO.getId() != null && organizerService.isAlreadyExists(organizerDTO)) {
            return null;
        }

        return organizerService.saveOrganizer(organizerDTO);
    }

    @GetMapping(value = "/fetch/page")
    public List<OrganizerResponseDTO> fetchOrganizersPage(@RequestBody OrganizerSearchParams organizerSearchParams) {
        if (organizerSearchParams.getPageNumber() == null || organizerSearchParams.getPageSize() == null) {
            return null;
        }

        return organizerService.fetchOrganizersPage(organizerSearchParams).getContent();
    }

    @GetMapping(value = "/fetch/list")
    public List<OrganizerResponseDTO> fetchOrganizersList(@RequestBody OrganizerSearchParams organizerSearchParams) {
        return organizerService.fetchOrganizersList(organizerSearchParams);
    }

    @PatchMapping(value = "/update")
    public OrganizerResponseDTO updateOrganizer(@RequestBody OrganizerDTO organizerDTO) {
        if (organizerDTO.getId() == null || !organizerService.isAlreadyExists(organizerDTO)) {
            return null;
        }

        return organizerService.saveOrganizer(organizerDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
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

}
