package dev.gui.desafio_nubank.modules.contatos.mapper;

import dev.gui.desafio_nubank.modules.clientes.entity.Clientes;
import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ContatosMapper {

    public Contatos toEntity(ContatosRequestDTO requestDTO, Clientes cliente) {
        if (requestDTO == null) return null;

        var contato = new Contatos();
        contato.setNome(requestDTO.nome());
        contato.setEmail(requestDTO.email());
        contato.setTelefone(requestDTO.telefone());

        contato.setClientes(cliente);

        return contato;
    }


    public ContatosResponseDTO toResponse(Contatos contato) {
        if (contato == null) return null;

        Long clienteId = null;
        if (contato.getClientes() != null) {
            clienteId = contato.getClientes().getId();
        }

        return new ContatosResponseDTO(
                clienteId,
                contato.getNome(),
                contato.getEmail(),
                contato.getTelefone()
        );
    }
}