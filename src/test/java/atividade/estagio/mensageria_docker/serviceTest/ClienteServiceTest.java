package atividade.estagio.mensageria_docker.serviceTest;


import atividade.estagio.mensageria_docker.entity.Cliente;
import atividade.estagio.mensageria_docker.mensageria.RecargaProducer;
import atividade.estagio.mensageria_docker.repository.ClienteRepository;
import atividade.estagio.mensageria_docker.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {


    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RecargaProducer recargaProducer;


    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve retornar uma lista e retornar o numero de cadastros")
    void deveRetornarListaClientes() {
        //Arrange (Preparar / Montar o cenário)
        Cliente cliente = new Cliente("Miguel", "miguelsr@ufu.br");
        Mockito.when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

        //Act (Agir / Executar)
        List<Cliente> lista = clienteService.listarClientes();

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(1, lista.size());

    }

    @Test
    @DisplayName("Deve criar um cliente e verificar seus dados")
    void deveCriarCliente() {
        //Arrange (Preparar / Montar o cenário)
        Cliente cliente = new Cliente("Miguel", "miguelsr@ufu.br");
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        //Act (Agir / Executar)
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(novoCliente);
        Assertions.assertEquals("Miguel", novoCliente.getNome());
        Assertions.assertEquals("miguelsr@ufu.br", novoCliente.getEmail());

    }

    @Test
    void deveEditarCliente() {
        //Arrange (Preparar / Montar o cenário)
        Cliente cliente = new Cliente("Miguel", "miguelsr@ufu.br");
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        //Act (Agir / Executar)
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(novoCliente);
        Assertions.assertEquals("Miguel", novoCliente.getNome());
        Assertions.assertEquals("miguelsr@ufu.br", novoCliente.getEmail());

    }

    @Test
    @DisplayName("Deve excluir o cliente pelo id quando ele existir")
    void deveExcluirCliente() throws Exception {
        Long id = 1L;

        // Arrange
        Mockito.when(clienteRepository.existsById(id)).thenReturn(true); // Simula que o passageiro existe
        Mockito.doNothing().when(clienteRepository).deleteById(id);

        // Act
        clienteService.excluirCliente(id);

        // Assert
        // Verifica se o deleteById foi realmente chamado uma vez
        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(id);
    }


}