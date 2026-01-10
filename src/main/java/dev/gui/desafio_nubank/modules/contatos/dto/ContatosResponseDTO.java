package dev.gui.desafio_nubank.modules.contatos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContatosResponseDTO {

    private Long clienteId;
    private String nome;
    private String email;
    private String telefone;

}
