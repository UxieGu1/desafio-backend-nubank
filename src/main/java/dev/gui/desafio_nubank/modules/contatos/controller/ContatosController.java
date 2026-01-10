package dev.gui.desafio_nubank.modules.contatos.controller;

// Removi o import errado do logback
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import dev.gui.desafio_nubank.modules.contatos.service.ContatosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contatos")
public class ContatosController {

    private final ContatosService contatosService;

    public ContatosController(ContatosService contatosService) {
        this.contatosService = contatosService;
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarContatoParaClienteExistente(@RequestBody @Valid ContatosRequestDTO requestDTO){
        try{
            ContatosResponseDTO response = contatosService.cadastrarContatoParaClienteExistente(requestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}