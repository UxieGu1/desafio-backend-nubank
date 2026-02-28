package dev.gui.desafio_nubank.modules.clientes.service;

import dev.gui.desafio_nubank.modules.clientes.entity.Clientes;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesRequestDTO;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesResponseDTO;
import dev.gui.desafio_nubank.modules.clientes.mapper.ClientesMapper;
import dev.gui.desafio_nubank.modules.clientes.repository.ClientesRepository;
import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientesService {

    private final ClientesRepository clientesRepository;
    private final ClientesMapper clientesMapper;

    @Transactional
    public ClientesResponseDTO cadastrarCliente(ClientesRequestDTO clientesRequest){
        Clientes cliente = clientesMapper.toEntity(clientesRequest);
        Clientes clienteSalvo = clientesRepository.save(cliente);

        return clientesMapper.toResponse(clienteSalvo);
    }

    public List<ClientesResponseDTO> listarClientes(){
        return clientesRepository
                .findAll()
                .stream()
                .map(clientesMapper::toResponse)
                .toList();
    }

    public ClientesResponseDTO listarClientePorId(Long id){
        var clientePorId = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: "+ id));
        return clientesMapper.toResponse(clientePorId);
    }

    @Transactional
    public ClientesResponseDTO atualizarCliente(Long id, ClientesRequestDTO clientesRequest) {

        var cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + id + " não encontrado."));

        cliente.setNome(clientesRequest.nome());
        cliente.setEmail(clientesRequest.email());
        cliente.getContatos().clear();

        if (clientesRequest.contatos() != null) {
            var novosContatos = clientesRequest.contatos().stream()
                    .map(dto -> {
                        var contato = new Contatos();
                        contato.setNome(dto.nome());
                        contato.setEmail(dto.email());
                        contato.setTelefone(dto.telefone());
                        contato.setClientes(cliente);
                        return contato;
                    }).toList();

            cliente.getContatos().addAll(novosContatos);
        }
        var clienteAtualizado = clientesRepository.save(cliente);
        return clientesMapper.toResponse(clienteAtualizado);
    }

    public void deletarCliente(Long id){
        var cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + id + " não encontrado."));

        clientesRepository.delete(cliente);
    }
}