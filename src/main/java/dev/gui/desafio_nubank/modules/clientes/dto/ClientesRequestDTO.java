package dev.gui.desafio_nubank.modules.clientes.dto;

import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;


public record ClientesRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3-100 caracteres")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Valid
        List<ContatosRequestDTO> contatos
) {
}
