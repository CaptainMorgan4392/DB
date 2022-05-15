package ru.nsu.kosarev.db.artist;

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
import ru.nsu.kosarev.db.artist.dto.ArtistDTO;
import ru.nsu.kosarev.db.artist.dto.ArtistImpresarioJenreDto;
import ru.nsu.kosarev.db.artist.dto.ArtistResponseDTO;
import ru.nsu.kosarev.db.artist.projections.ArtistImpresarioJenreProjection;
import ru.nsu.kosarev.db.artist.sortingfilter.ArtistSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PutMapping(value = "/add")
    public ArtistResponseDTO createArtist(@RequestBody ArtistDTO artistDTO) {
        if (artistDTO.getId() != null && artistService.isAlreadyExists(artistDTO)) {
            return null;
        }

        return artistService.saveArtist(artistDTO);
    }

    @GetMapping(value = "/fetch/page")
    public List<ArtistResponseDTO> fetchArtistsPage(@RequestBody ArtistSearchParams artistSearchParams) {
        if (artistSearchParams.getPageNumber() == null || artistSearchParams.getPageSize() == null) {
            return null;
        }

        return artistService.fetchArtistsPage(artistSearchParams).getContent();
    }

    @GetMapping(value = "/fetch/list")
    public List<ArtistResponseDTO> fetchArtistsList(@RequestBody ArtistSearchParams artistSearchParams) {
        return artistService.fetchArtistsList(artistSearchParams);
    }

    @PatchMapping(value = "/update")
    public ArtistResponseDTO updateArtist(@RequestBody ArtistDTO artistDTO) {
        if (artistDTO.getId() == null || !artistService.isAlreadyExists(artistDTO)) {
            return null;
        }

        return artistService.saveArtist(artistDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteArtist(@PathVariable("id") Integer artistId) {
        artistService.deleteArtist(artistId);
    }

    @PostMapping(value = "/artistWithImpresario/add")
    public void makeArtistWorkingWithImpresarioInJenre(@RequestBody ArtistImpresarioJenreDto artistImpresarioJenreDto) {
        artistService.makeArtistWorkingWithImpresarioInJenre(artistImpresarioJenreDto);
    }

    @GetMapping(value = "/artistWithImpresario/fetch")
    public List<ArtistImpresarioJenreProjection> getArtistWorkingWithImpresarioInJenre() {
        return artistService.getArtistWorkingWithImpresarioInJenre();
    }

    @PatchMapping(value = "/artistWithImpresario/update")
    public void updateArtistWorkingWithImpresarioInJenre(@RequestBody ArtistImpresarioJenreDto artistImpresarioJenreDto) {

    }

    @DeleteMapping(value = "/artistWithImpresario/delete/{id}")
    public void deleteArtistWorkingWithImpresarioInJenre(@PathVariable("id") Integer id) {

    }

}
