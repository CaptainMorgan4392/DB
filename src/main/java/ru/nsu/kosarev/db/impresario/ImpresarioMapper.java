package ru.nsu.kosarev.db.impresario;

import ru.nsu.kosarev.db.common.utils.DateTimeFormatter;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioDTO;
import ru.nsu.kosarev.db.impresario.dto.ImpresarioResponseDTO;

public class ImpresarioMapper {

    static ImpresarioResponseDTO toImpresarioDTO(Impresario impresario) {
        return new ImpresarioResponseDTO(
            impresario.getId(),
            impresario.getName(),
            impresario.getSurname(),
            DateTimeFormatter.getFormattedDateFromTimestamp(impresario.getBirthDate())
        );
    }

    static Impresario toImpresario(ImpresarioDTO impresarioDTO) {
        return new Impresario(
            impresarioDTO.getId(),
            impresarioDTO.getName(),
            impresarioDTO.getSurname(),
            impresarioDTO.getBirthDate()
        );
    }

}
