package dev.gui.desafio_nubank.modules.clientes.mapper;

import dev.gui.desafio_nubank.modules.clientes.dto.ClientesResponseDTO;
import dev.gui.desafio_nubank.modules.clientes.entity.Clientes;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientesMapper {

    public Clientes toEntity(ClientesRequestDTO clientesRequest){
        if(clientesRequest == null) return null;

        var cliente = Clientes.builder()
                .nome(clientesRequest.nome())
                .email(clientesRequest.email())
                .build();

        if (clientesRequest.contatos() != null) {
            var contatosEntityList = clientesRequest.contatos().stream()
                    .map(dto -> {
                        var contato = new Contatos();
                        contato.setNome(dto.nome());
                        contato.setEmail(dto.email());
                        contato.setTelefone(dto.telefone());

                        contato.setClientes(cliente);

                        return contato;
                    }).toList();

            cliente.setContatos(contatosEntityList);
        }

        return cliente;
    }

    public ClientesResponseDTO toResponse(Clientes clientes) {
        if (clientes == null) return null;

        List<ContatosResponseDTO> contatosResponseList = null;

        if (clientes.getContatos() != null) {
            contatosResponseList = clientes.getContatos().stream()
                    .map(contato -> new ContatosResponseDTO(
                            clientes.getId(),
                            contato.getNome(),
                            contato.getEmail(),
                            contato.getTelefone()
                    )).toList();
        }

        return new ClientesResponseDTO(
                clientes.getId(),
                clientes.getNome(),
                clientes.getEmail(),
                contatosResponseList,
                clientes.getCreatedAt(),
                clientes.getUpdatedAt()
        );
    }
}
