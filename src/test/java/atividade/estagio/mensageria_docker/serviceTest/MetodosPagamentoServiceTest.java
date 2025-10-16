package atividade.estagio.mensageria_docker.serviceTest;


import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.repository.MetodoPagamentoRepository;
import atividade.estagio.mensageria_docker.service.MetodoPagamentoService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MetodosPagamentoServiceTest {


    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;


    @InjectMocks
    private MetodoPagamentoService metodoPagamentoService;

    @Test
    @DisplayName("Deve verificar se a linha possui tamanho 1")
    void deveRetornarListaMetodos() {
        //Arrange (Preparar / Montar o cenário)
        MetodoPagamento metodoPagamento = new MetodoPagamento("PIX");
        Mockito.when(metodoPagamentoRepository.findAll()).thenReturn(Collections.singletonList(metodoPagamento));

        //Act (Agir / Executar)
        List<MetodoPagamento> lista = metodoPagamentoService.listarTodosPagamentos();

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(1, lista.size());
        Assertions.assertEquals("PIX", lista.get(0).getTipo());
        verify(metodoPagamentoRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deve criar um metodo e verificar seu tipo")
    void deveCriarMetodoPagamento() {
        //Arrange (Preparar / Montar o cenário)
        MetodoPagamento metodoPagamento = new MetodoPagamento("PIX");
        Mockito.when(metodoPagamentoRepository.save(metodoPagamento)).thenReturn(metodoPagamento);

        //Act (Agir / Executar)
        MetodoPagamento novoMetodo = metodoPagamentoService.novoPagamento(metodoPagamento);

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(novoMetodo);
        Assertions.assertEquals("PIX", novoMetodo.getTipo());
        verify(metodoPagamentoRepository, times(1)).save(novoMetodo);
    }

    @Test
    @DisplayName("Deve editar um metodo e verificar seu tipo")
    void deveEditarMetodo() {
        //Arrange (Preparar / Montar o cenário)
        MetodoPagamento metodoPagamento = new MetodoPagamento("PIX");
        metodoPagamento.setTipo("BOLETO");
        Mockito.when(metodoPagamentoRepository.save(metodoPagamento)).thenReturn(metodoPagamento);

        //Act (Agir / Executar)
        MetodoPagamento novoMetodo = metodoPagamentoService.novoPagamento(metodoPagamento);

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(novoMetodo);
        Assertions.assertEquals("BOLETO", novoMetodo.getTipo());
        verify(metodoPagamentoRepository, times(1)).save(novoMetodo);

    }

    @Test
    @DisplayName("Deve excluir o cliente pelo id quando ele existir")
    void deveExcluirCliente() throws Exception {
        Long id = 1L;

        // Arrange
        Mockito.when(metodoPagamentoRepository.existsById(id)).thenReturn(true); // Simula que o metodo existe
        Mockito.doNothing().when(metodoPagamentoRepository).deleteById(id);

        // Act
        metodoPagamentoService.excluirPagamento(id);

        // Assert
        // Verifica se o deleteById foi realmente chamado uma vez
        Mockito.verify(metodoPagamentoRepository, Mockito.times(1)).deleteById(id);
    }


}