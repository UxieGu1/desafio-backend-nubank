package dev.gui.desafio_nubank.modules.clientes.service;

import dev.gui.desafio_nubank.modules.clientes.dto.ClientesRequestDTO;
import dev.gui.desafio_nubank.modules.clientes.dto.ClientesResponseDTO;
import dev.gui.desafio_nubank.modules.clientes.entity.Clientes;
import dev.gui.desafio_nubank.modules.clientes.mapper.ClientesMapper;
import dev.gui.desafio_nubank.modules.clientes.repository.ClientesRepository;
import dev.gui.desafio_nubank.modules.contatos.dto.ContatosRequestDTO;
import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientesServiceTest {

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private ClientesMapper clientesMapper;

    @InjectMocks
    private ClientesService clientesService;

    private Clientes clienteMock;
    private ClientesRequestDTO requestDTOMock;
    private ClientesResponseDTO responseDTOMock;
    private final Long CLIENTE_ID = 1L;

    @BeforeEach
    void setUp() {
        clienteMock = new Clientes();
        clienteMock.setId(CLIENTE_ID);
        clienteMock.setNome("João Silva");
        clienteMock.setEmail("joao@email.com");
        clienteMock.setContatos(new ArrayList<>());

        requestDTOMock = new ClientesRequestDTO("João Silva", "joao@email.com", List.of());

        responseDTOMock = new ClientesResponseDTO(
                CLIENTE_ID,
                "João Silva",
                "joao@email.com",
                List.of(),
                LocalDateTime.now(),
                null
                );
    }

    @Test
    @DisplayName("Deve cadastrar um cliente com sucesso")
    void cadastrarCliente_ComSucesso() {
        // Arrange (Configuração dos Mocks)
        when(clientesMapper.toEntity(any(ClientesRequestDTO.class))).thenReturn(clienteMock);
        when(clientesRepository.save(any(Clientes.class))).thenReturn(clienteMock);
        when(clientesMapper.toResponse(any(Clientes.class))).thenReturn(responseDTOMock);

        // Act (Ação)
        ClientesResponseDTO resultado = clientesService.cadastrarCliente(requestDTOMock);

        // Assert (Verificação)
        assertNotNull(resultado);
        assertEquals(responseDTOMock.nome(), resultado.nome());

        verify(clientesMapper, times(1)).toEntity(any());
        verify(clientesRepository, times(1)).save(any());
        verify(clientesMapper, times(1)).toResponse(any());
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void listarClientes_ComSucesso() {
        // Arrange
        when(clientesRepository.findAll()).thenReturn(List.of(clienteMock));
        when(clientesMapper.toResponse(clienteMock)).thenReturn(responseDTOMock);

        // Act
        List<ClientesResponseDTO> resultado = clientesService.listarClientes();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clientesRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar um cliente por ID com sucesso")
    void listarClientePorId_ComSucesso() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteMock));
        when(clientesMapper.toResponse(clienteMock)).thenReturn(responseDTOMock);

        // Act
        ClientesResponseDTO resultado = clientesService.listarClientePorId(CLIENTE_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(CLIENTE_ID, resultado.id());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente por ID inexistente")
    void listarClientePorId_ClienteNaoEncontrado_LancaExcecao() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientesService.listarClientePorId(CLIENTE_ID);
        });

        assertEquals("Cliente não encontrado com o ID: " + CLIENTE_ID, exception.getMessage());
        verify(clientesMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void atualizarCliente_ComSucesso() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteMock));
        when(clientesRepository.save(any(Clientes.class))).thenReturn(clienteMock);
        when(clientesMapper.toResponse(any(Clientes.class))).thenReturn(responseDTOMock);

        // Act
        ClientesResponseDTO resultado = clientesService.atualizarCliente(CLIENTE_ID, requestDTOMock);

        // Assert
        assertNotNull(resultado);
        verify(clientesRepository, times(1)).findById(CLIENTE_ID);
        verify(clientesRepository, times(1)).save(clienteMock);
    }

    @Test
    @DisplayName("Deve deletar um cliente com sucesso")
    void deletarCliente_ComSucesso() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteMock));
        doNothing().when(clientesRepository).delete(clienteMock); // Para métodos void

        // Act
        clientesService.deletarCliente(CLIENTE_ID);

        // Assert
        verify(clientesRepository, times(1)).findById(CLIENTE_ID);
        verify(clientesRepository, times(1)).delete(clienteMock);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar cliente inexistente")
    void deletarCliente_ClienteNaoEncontrado_LancaExcecao() {
        // Arrange
        when(clientesRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientesService.deletarCliente(CLIENTE_ID);
        });

        assertEquals("Cliente com ID " + CLIENTE_ID + " não encontrado.", exception.getMessage());
        verify(clientesRepository, never()).delete(any());
    }
}