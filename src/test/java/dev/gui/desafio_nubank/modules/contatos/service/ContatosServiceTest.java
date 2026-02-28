package dev.gui.desafio_nubank.modules.contatos.service;

import dev.gui.desafio_nubank.modules.clientes.entity.Clientes;
import dev.gui.desafio_nubank.modules.clientes.repository.ClientesRepository;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosResponseDTO;
import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import dev.gui.desafio_nubank.modules.contatos.repository.ContatosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatosServiceTest {

    @Mock
    private ContatosRepository contatosRepository;

    @Mock
    private ClientesRepository clientesRepository;

    @InjectMocks
    private ContatosService contatosService;

    private Clientes clienteMock;
    private Contatos contatoMock;
    private ContatosRequestDTO requestDTOMock;
    private final Long CLIENTE_ID = 1L;
    private final Long CONTATO_ID = 100L;

    @BeforeEach
    void setUp() {
        // Mock do Cliente
        clienteMock = new Clientes();
        clienteMock.setId(CLIENTE_ID);
        clienteMock.setNome("Maria Silva");

        // Mock do Contato
        contatoMock = new Contatos();
        contatoMock.setId(CONTATO_ID);
        contatoMock.setNome("Maria Contato");
        contatoMock.setEmail("maria@contato.com");
        contatoMock.setTelefone("11999999999");
        contatoMock.setClientes(clienteMock);

        requestDTOMock = new ContatosRequestDTO(
                CLIENTE_ID,
                "Maria Contato",
                "maria@contato.com",
                "11999999999"
        );
    }

    @Test
    @DisplayName("Deve cadastrar um contato com sucesso para um cliente existente")
    void cadastrarContatoParaClienteExistente_ComSucesso() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteMock));
        when(contatosRepository.save(any(Contatos.class))).thenReturn(contatoMock);

        // Act
        ContatosResponseDTO resultado = contatosService.cadastrarContatoParaClienteExistente(requestDTOMock);

        // Assert
        assertNotNull(resultado);
        assertEquals(CONTATO_ID, resultado.clienteId());
        assertEquals("Maria Contato", resultado.nome());

        verify(clientesRepository, times(1)).findById(CLIENTE_ID);
        verify(contatosRepository, times(1)).save(any(Contatos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se o ID do cliente for nulo ao cadastrar contato")
    void cadastrarContato_IdClienteNulo_LancaExcecao() {
        // Arrange
        ContatosRequestDTO requestInvalido = new ContatosRequestDTO(
                null, "Teste", "teste@email.com", "123"
        );

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatosService.cadastrarContatoParaClienteExistente(requestInvalido);
        });

        // Agora sim a mensagem vai bater!
        assertEquals("O ID do cliente é obrigatório", exception.getMessage());
        verify(clientesRepository, never()).findById(any());
        verify(contatosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se o cliente não for encontrado ao cadastrar contato")
    void cadastrarContato_ClienteNaoEncontrado_LancaExcecao() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatosService.cadastrarContatoParaClienteExistente(requestDTOMock);
        });

        assertEquals("Cliente não encontrado com o ID: " + CLIENTE_ID, exception.getMessage());
        verify(contatosRepository, never()).save(any());
    }

    // ==========================================
    // TESTES PARA LISTAGEM DE CONTATOS POR CLIENTE
    // ==========================================

    @Test
    @DisplayName("Deve listar contatos por ID de cliente com sucesso")
    void listagemDeContatosPorClienteId_ComSucesso() {
        // Arrange
        when(clientesRepository.existsById(CLIENTE_ID)).thenReturn(true);
        when(contatosRepository.findByClientesId(CLIENTE_ID)).thenReturn(List.of(contatoMock));

        // Act
        List<ContatosResponseDTO> resultado = contatosService.listagemDeContatosPorClienteId(CLIENTE_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(CONTATO_ID, resultado.get(0).clienteId());

        verify(clientesRepository, times(1)).existsById(CLIENTE_ID);
        verify(contatosRepository, times(1)).findByClientesId(CLIENTE_ID);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar listar contatos de um cliente inexistente")
    void listagemDeContatosPorClienteId_ClienteNaoEncontrado_LancaExcecao() {
        // Arrange
        when(clientesRepository.existsById(CLIENTE_ID)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatosService.listagemDeContatosPorClienteId(CLIENTE_ID);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(contatosRepository, never()).findByClientesId(any());
    }
}