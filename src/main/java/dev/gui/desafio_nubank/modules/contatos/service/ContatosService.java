package dev.gui.desafio_nubank.modules.contatos.service;

import dev.gui.desafio_nubank.modules.clientes.Clientes;
import dev.gui.desafio_nubank.modules.clientes.repository.ClientesRepository;
import dev.gui.desafio_nubank.modules.contatos.Contatos;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import dev.gui.desafio_nubank.modules.contatos.repository.ContatosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ContatosService {

    private final ContatosRepository contatosRepository;
    private final ClientesRepository clientesRepository;

    public ContatosService(ContatosRepository contatosRepository, ClientesRepository clientesRepository) {
        this.contatosRepository = contatosRepository;
        this.clientesRepository = clientesRepository;
    }

    @Transactional
    public ContatosResponseDTO cadastrarContatoParaClienteExistente(ContatosRequestDTO requestDTO){
        if(requestDTO.getClienteId() == null){
            throw new RuntimeException("O ID do cliente é obrigatório");
        }

        Clientes cliente = clientesRepository.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + requestDTO.getClienteId()));

        Contatos contato = new Contatos();
        contato.setNome(requestDTO.getNome());
        contato.setEmail(requestDTO.getEmail());
        contato.setTelefone(requestDTO.getTelefone());

        contato.setClientes(cliente);

        Contatos contatoSalvo = contatosRepository.save(contato);

        return new ContatosResponseDTO(
                contatoSalvo.getId(),
                contatoSalvo.getNome(),
                contatoSalvo.getEmail(),
                contatoSalvo.getTelefone()
        );
        
    }

    public List<Contatos> listagemDeContatosPorClienteId(Long clienteId){
        if(contatosRepository.findById(clienteId).isEmpty()){
            throw new RuntimeException("O ID é obrigatório");
        }

        return contatosRepository.findAllById(Collections.singleton(clienteId));
    }
}
