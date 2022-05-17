package ru.nsu.kosarev.db.artist;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.dto.ArtistResponseDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistWithEventsDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistsEventPlacesDTO;
import ru.nsu.kosarev.db.artist.dto.PeriodDTO;
import ru.nsu.kosarev.db.artist.projections.ArtistEventProjection;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.artist.projections.ArtistProjection;
import ru.nsu.kosarev.db.artist.sortingfilter.ArtistSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping(
        value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ArtistResponseDTO createArtist(@RequestBody ArtistDTO artistDTO) {
        if (artistDTO.getId() != null && artistService.isAlreadyExists(artistDTO)) {
            return null;
        }

        return artistService.saveArtist(artistDTO);
    }

    @PostMapping(
        value = "/fetch/page",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ArtistResponseDTO> fetchArtistsPage(@RequestBody ArtistSearchParams artistSearchParams) {
        if (artistSearchParams.getPageNumber() == null || artistSearchParams.getPageSize() == null) {
            return null;
        }

        return artistService.fetchArtistsPage(artistSearchParams).getContent();
    }

    @PostMapping(
        value = "/fetch/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ArtistResponseDTO> fetchArtistsList(@RequestBody ArtistSearchParams artistSearchParams) {
        return artistService.fetchArtistsList(artistSearchParams);
    }

    @PostMapping(
        value = "/update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ArtistResponseDTO updateArtist(@RequestBody ArtistDTO artistDTO) {
        if (artistDTO.getId() == null || !artistService.isAlreadyExists(artistDTO)) {
            return null;
        }

        return artistService.saveArtist(artistDTO);
    }

    @PostMapping(value = "/delete/{id}")
    public void deleteArtist(@PathVariable("id") Integer artistId) {
        artistService.deleteArtist(artistId);
    }

    @PostMapping(value = "/artistWithImpresario/add")
    public void makeArtistWorkingWithImpresarioInJenre(@RequestBody ArtistImpresarioJenreDto artistImpresarioJenreDto) {
        artistService.makeArtistWorkingWithImpresarioInJenre(artistImpresarioJenreDto);
    }

    @PostMapping(value = "/artistWithImpresario/fetch")
    public List<ArtistImpresarioJenreProjection> getArtistWorkingWithImpresarioInJenre() {
        return artistService.getArtistWorkingWithImpresarioInJenre();
    }

    @PostMapping(value = "/artistWithImpresario/delete/{id}")
    public void deleteArtistWorkingWithImpresarioInJenre(@PathVariable("id") Integer id) {
        artistService.deleteArtistWithImpresarioInJenre(id);
    }

    @PostMapping(value = "/artistsInJenre/{id}")
    public List<ArtistProjection> getArtistsInJenre(@PathVariable("id") Integer jenreId) {
        return artistService.getArtistsInJenre(jenreId);
    }

    @PostMapping(value = "/artistsNotTakingPartInPeriod")
    public List<ArtistProjection> getArtistsNotTakingPartInPeriod(@RequestBody PeriodDTO periodDTO) {
        return artistService.getArtistsNotTakingPartInPeriod(periodDTO.getFrom(), periodDTO.getTo());
    }

    @PostMapping(value = "/organizerEvent/{art_id}/{event_id}")
    public void bindOrganizerToEvent(
        @PathVariable("art_id") Integer artistId,
        @PathVariable("event_id") Integer eventId
    ) {
        artistService.bindArtistToEvent(artistId, eventId);
    }

    @PostMapping(value = "/organizerEvent/get/{id}")
    public List<ArtistWithEventsDTO> getEventsOfOrganizer(@PathVariable("id") Integer artistId) {
        return artistService.getEventsOfArtist(artistId);
    }

    @PostMapping(value = "/organizerEvent/delete/{art_id}/{event_id}")
    public void deleteEventOfOrganizer(
        @PathVariable("art_id") Integer organizerId,
        @PathVariable("event_id") Integer eventId
    ) {
        artistService.deleteEventOfArtist(organizerId, eventId);
    }

    @PostMapping(value = "/artistsWithPlaces")
    public void bindArtistsToPlacesInEvent(ArtistsEventPlacesDTO artistsEventPlacesDTO) {
        artistService.bindArtistsToPlacesInEvent(artistsEventPlacesDTO);
    }

}
