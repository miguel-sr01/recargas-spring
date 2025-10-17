package atividade.estagio.mensageria_docker.serviceTest;


import atividade.estagio.mensageria_docker.entity.Cliente;
import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.entity.StatusRecarga;
import atividade.estagio.mensageria_docker.mensageria.RecargaProducer;
import atividade.estagio.mensageria_docker.repository.MetodoPagamentoRepository;
import atividade.estagio.mensageria_docker.repository.RecargaRepository;
import atividade.estagio.mensageria_docker.service.RecargaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecargaServiceTest {



    @Mock
    private RecargaRepository recargaRepository;

    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;


    @Mock
    private RecargaProducer recargaProducer;


    @InjectMocks
    private RecargaService recargaService;

    @Test
    @DisplayName("Deve verificar se a linha possui tamanho 1")
    void deveRetornarListaClientes() {
        //Arrange (Preparar / Montar o cenário)
        Recarga recarga = new Recarga("123456", 50, StatusRecarga.SUCESSO, "1010");
        Mockito.when(recargaRepository.findAll()).thenReturn(Collections.singletonList(recarga));

        //Act (Agir / Executar)
        List<Recarga> lista = recargaService.listarRecargas();

        //Assert (Verificar / Afirmar)
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(1, lista.size());
        Assertions.assertEquals("123456", lista.get(0).getNumeroCelular());
        verify(recargaRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deve criar uma recarga e verificar seu STATUS")
    void deveCriarRecarga() {
        // Arrange (monta o cenário)
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        MetodoPagamento metodo = new MetodoPagamento();
        metodo.setId(10L);
        metodo.setCliente(cliente);

        Recarga recarga = new Recarga("123456", 50, StatusRecarga.SUCESSO, "1010");
        recarga.setCliente(cliente);
        recarga.setMetodoPagamento(metodo);

        // Mock do repositório de metodo de pagamento
        Mockito.when(metodoPagamentoRepository.findById(10L)).thenReturn(Optional.of(metodo));

        // Mock do save
        Mockito.when(recargaRepository.save(recarga)).thenReturn(recarga);

        // Act (executa)
        Recarga novaRecarga = recargaService.novaRecarga(recarga);

        // Assert (verifica)
        Assertions.assertNotNull(novaRecarga);
        Assertions.assertEquals("123456", novaRecarga.getNumeroCelular());
        Assertions.assertEquals(StatusRecarga.PENDENTE, novaRecarga.getStatus());
        verify(recargaRepository, times(1)).save(recarga);
        verify(recargaProducer, times(1)).enviarRecarga(recarga);

    }

    @Test
    @DisplayName("Deve editar um metodo e verificar seu numero de celular")
    void deveEditarRecarga() {
        // Arrange (monta o cenário)
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        MetodoPagamento metodo = new MetodoPagamento();
        metodo.setId(10L);
        metodo.setCliente(cliente);

        Recarga recarga = new Recarga("123456", 50, StatusRecarga.SUCESSO, "1010");
        recarga.setCliente(cliente);
        recarga.setMetodoPagamento(metodo);
        recarga.setNumeroCelular("789456");

        // Mock do repositório de metodo de pagamento
        Mockito.when(metodoPagamentoRepository.findById(10L)).thenReturn(Optional.of(metodo));

        // Mock do save
        Mockito.when(recargaRepository.save(recarga)).thenReturn(recarga);

        // Act (executa)
        Recarga novaRecarga = recargaService.novaRecarga(recarga);

        // Assert (verifica)
        Assertions.assertNotNull(novaRecarga);
        Assertions.assertEquals("789456", novaRecarga.getNumeroCelular());
        Assertions.assertEquals(StatusRecarga.PENDENTE, novaRecarga.getStatus());
        verify(recargaRepository, times(1)).save(recarga);
        verify(recargaProducer, times(1)).enviarRecarga(recarga);

    }

    @Test
    @DisplayName("Deve excluir a recarga pelo id quando ela existir")
    void deveExcluirRecarga() throws Exception {
        Long id = 1L;

        // Arrange
        Mockito.when(recargaRepository.existsById(id)).thenReturn(true); // Simula que a recarga existe
        Mockito.doNothing().when(recargaRepository).deleteById(id);

        // Act
        recargaService.excluirRecarga(id);

        // Assert
        // Verifica se o deleteById foi realmente chamado uma vez
        Mockito.verify(recargaRepository, Mockito.times(1)).deleteById(id);
    }


}