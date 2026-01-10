package dev.gui.desafio_nubank.modules.clientes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientesResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private List<ContatosResponseDTO> contatos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
