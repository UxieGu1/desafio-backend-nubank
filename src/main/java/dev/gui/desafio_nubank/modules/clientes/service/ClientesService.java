package dev.gui.desafio_nubank.modules.clientes.service;

import dev.gui.desafio_nubank.modules.clientes.Clientes;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesRequestDTO;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesResponseDTO;
import dev.gui.desafio_nubank.modules.clientes.repository.ClientesRepository;
import dev.gui.desafio_nubank.modules.contatos.Contatos;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientesService {

    private final ClientesRepository clientesRepository;

    public ClientesService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @Transactional
    public ClientesResponseDTO cadastrarCliente(ClientesRequestDTO requestDTO) {
        if (clientesRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Clientes cliente = new Clientes();
        cliente.setNome(requestDTO.getNome());
        cliente.setEmail(requestDTO.getEmail());

        if (requestDTO.getContatos() != null && !requestDTO.getContatos().isEmpty()) {
            List<Contatos> contatosEntities = requestDTO.getContatos().stream()
                    .map(contatoDTO -> {
                        Contatos contato = new Contatos();
                        contato.setNome(contatoDTO.getNome());
                        contato.setEmail(contatoDTO.getEmail());
                        contato.setTelefone(contatoDTO.getTelefone());

                        contato.setClientes(cliente);
                        return contato;
                    }).collect(Collectors.toList());

            cliente.setContatos(contatosEntities);
        }

        Clientes clienteSalvo = clientesRepository.save(cliente);

        List<ContatosResponseDTO> contatosResponse = clienteSalvo.getContatos() != null ?
                clienteSalvo.getContatos().stream()
                        .map(c -> new ContatosResponseDTO(
                                c.getId(),
                                c.getNome(),
                                c.getEmail(),
                                c.getTelefone()
                        ))
                        .collect(Collectors.toList()) : Collections.emptyList();

        return ClientesResponseDTO.builder()
                .id(clienteSalvo.getId())
                .nome(clienteSalvo.getNome())
                .email(clienteSalvo.getEmail())
                .contatos(contatosResponse)
                .createdAt(clienteSalvo.getCreatedAt())
                .updatedAt(clienteSalvo.getUpdatedAt())
                .build();
    }

    public List<Clientes> listagemDeClientes(){
        return clientesRepository.findAll();
    }

}