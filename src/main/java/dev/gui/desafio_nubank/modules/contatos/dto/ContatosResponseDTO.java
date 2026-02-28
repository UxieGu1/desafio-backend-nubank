package dev.gui.desafio_nubank.modules.contatos.dto;



public record ContatosResponseDTO(
        Long clienteId,
        String nome,
        String email,
        String telefone
) {
}
