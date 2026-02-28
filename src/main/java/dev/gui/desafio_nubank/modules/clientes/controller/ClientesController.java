package dev.gui.desafio_nubank.modules.clientes.controller;

import dev.gui.desafio_nubank.modules.clientes.dto.ClientesRequestDTO;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesResponseDTO;
import dev.gui.desafio_nubank.modules.clientes.service.ClientesService;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import dev.gui.desafio_nubank.modules.contatos.service.ContatosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClientesService clientesService;
    private final ContatosService contatosService;


    @PostMapping()
    public ResponseEntity<Object> cadastrarCliente(@RequestBody @Valid ClientesRequestDTO requestDTO){
        try{
            ClientesResponseDTO responseDTO = clientesService.cadastrarCliente(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> listagemDeClientes() {
        try {
            List<ClientesResponseDTO> listaClientes = clientesService.listarClientes();

            return ResponseEntity.ok(listaClientes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{clienteId}/contatos")
    public ResponseEntity<Object> listarContatosPorCliente(@PathVariable Long clienteId){
        try{
            List<ContatosResponseDTO> listaContatos = contatosService.listagemDeContatosPorClienteId(clienteId);
            return ResponseEntity.ok(listaContatos);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
