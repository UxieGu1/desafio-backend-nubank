package dev.gui.desafio_nubank.modules.clientes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientesResponseDTO (
        Long id,
        String nome,
        String email,
        List<ContatosResponseDTO> contatos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
