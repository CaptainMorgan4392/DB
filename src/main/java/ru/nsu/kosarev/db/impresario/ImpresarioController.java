package ru.nsu.kosarev.db.impresario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.kosarev.db.impresario.dto.ArtistsOfAllImpresariosDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioResponseDTO;
import ru.nsu.kosarev.db.impresario.projections.ImpresarioArtistProjection;
import ru.nsu.kosarev.db.impresario.sortingfilter.ImpresarioSearchParams;

import java.util.List;

@RestController
@RequestMapping(value = "/impresario")
public class ImpresarioController {

    @Autowired
    private ImpresarioService impresarioService;

    @PutMapping(value = "/add")
    public ImpresarioResponseDTO createImpresario(@RequestBody ImpresarioDTO impresarioDTO) {
        if (impresarioDTO.getId() != null && impresarioService.isAlreadyExists(impresarioDTO)) {
            return null;
        }

        return impresarioService.saveImpresario(impresarioDTO);
    }

    @GetMapping(value = "/fetch/page")
    public List<ImpresarioResponseDTO> fetchImpresariosPage(@RequestBody ImpresarioSearchParams impresarioSearchParams) {
        if (impresarioSearchParams.getPageNumber() == null || impresarioSearchParams.getPageSize() == null) {
            return null;
        }

        return impresarioService.fetchImpresariosPage(impresarioSearchParams).getContent();
    }

    @GetMapping(value = "/fetch/list")
    public List<ImpresarioResponseDTO> fetchImpresariosList(@RequestBody ImpresarioSearchParams impresarioSearchParams) {
        return impresarioService.fetchImpresariosList(impresarioSearchParams);
    }

    @PatchMapping(value = "/update")
    public ImpresarioResponseDTO updateImpresario(@RequestBody ImpresarioDTO impresarioDTO) {
        if (impresarioDTO.getId() == null || !impresarioService.isAlreadyExists(impresarioDTO)) {
            return null;
        }

        return impresarioService.saveImpresario(impresarioDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteImpresario(@PathVariable("id") Integer impresarioId) {
        impresarioService.deleteImpresario(impresarioId);
    }

    @GetMapping(value = "/getArtistsInMultipleJenres/{id}")
    public List<ImpresarioArtistProjection> getArtistsInMultipleJenres(@PathVariable("id") Integer impresarioId) {
        return impresarioService.getArtistsInMultipleJenres(impresarioId);
    }

    @GetMapping(value = "/getArtistsOfAllImpresarios")
    public List<ArtistsOfAllImpresariosDTO> getArtistsOfAllImpresarios() {
        return impresarioService.getArtistsOfAllImpresarios();
    }

}
