package ru.nsu.kosarev.db.impresario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.impresario.dto.ArtistsOfAllImpresariosDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioResponseDTO;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioArtistProjectionWithoutIds;
import ru.nsu.kosarev.db.impresario.projection.ImpresarioProjection;
import ru.nsu.kosarev.db.impresario.sortingfilter.ImpresarioSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/impresario")
public class ImpresarioController {

    @Autowired
    private ImpresarioService impresarioService;

    @PostMapping(
        value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ImpresarioResponseDTO createImpresario(@RequestBody ImpresarioDTO impresarioDTO) {
        if (impresarioDTO.getId() != null && impresarioService.isAlreadyExists(impresarioDTO)) {
            return null;
        }

        return impresarioService.saveImpresario(impresarioDTO);
    }

    @PostMapping(value = "/fetch/page",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ImpresarioResponseDTO> fetchImpresariosPage(@RequestBody ImpresarioSearchParams impresarioSearchParams) {
        if (impresarioSearchParams.getPageNumber() == null || impresarioSearchParams.getPageSize() == null) {
            return null;
        }

        return impresarioService.fetchImpresariosPage(impresarioSearchParams).getContent();
    }

    @PostMapping(value = "/fetch/list",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ImpresarioResponseDTO> fetchImpresariosList(@RequestBody ImpresarioSearchParams impresarioSearchParams) {
        return impresarioService.fetchImpresariosList(impresarioSearchParams);
    }

    @PostMapping(value = "/update",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ImpresarioResponseDTO updateImpresario(@RequestBody ImpresarioDTO impresarioDTO) {
        if (impresarioDTO.getId() == null || !impresarioService.isAlreadyExists(impresarioDTO)) {
            return null;
        }

        return impresarioService.saveImpresario(impresarioDTO);
    }

    @PostMapping(value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteImpresario(@PathVariable("id") Integer impresarioId) {
        impresarioService.deleteImpresario(impresarioId);
    }

    @PostMapping(value = "/getArtistsInMultipleJenres/{id}")
    public List<ImpresarioArtistProjectionWithoutIds> getArtistsInMultipleJenres(@PathVariable("id") Integer impresarioId) {
        return impresarioService.getArtistsInMultipleJenres(impresarioId);
    }

    @PostMapping(value = "/getArtistsOfAllImpresarios")
    public List<ArtistsOfAllImpresariosDTO> getArtistsOfAllImpresarios() {
        return impresarioService.getArtistsOfAllImpresarios();
    }

    @PostMapping(value = "/getImpresariosOfArtist/{id}")
    public List<ImpresarioProjection> getImpresariosOfArtist(@PathVariable("id") Integer artistId) {
        return impresarioService.getImpresariosOfArtist(artistId);
    }

    @PostMapping(value = "/getImpresariosInJenre/{id}")
    public List<ImpresarioProjection> getImpresariosInJenre(@PathVariable("id") Integer jenreId) {
        return impresarioService.getImpresariosInJenre(jenreId);
    }

}
