package org.banreservas.interview.clientes;

import io.quarkus.panache.common.Parameters;
import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.banreservas.interview.clientes.dtos.CreateClienteDto;
import org.banreservas.interview.clientes.dtos.UpdateClienteDto;
import org.banreservas.interview.paises.Pais;
import org.banreservas.interview.paises.PaisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

@QuarkusComponentTest
public class ClienteServiceTest {
    @Inject
    ClienteService clienteService;

    @InjectMock
    PaisService paisService;

    @InjectMock
    ClienteRepository clienteRepository;

    @Test
    public void testCreateCliente_whenValidValues_returnCliente() {
        Mockito.when(paisService.getPaisByCCN3(Mockito.anyString())).thenReturn(new Pais());
        Mockito.doNothing().when(clienteRepository).persist(Mockito.any(Cliente.class));

        var createClienteDto = new CreateClienteDto();
        createClienteDto.pais = "DOM";

        Assertions.assertNotNull(clienteService.createCliente(createClienteDto));
    }

    @Test
    public void testCreateCliente_whenNotFoundPais_throwBadRequestException() {
        Mockito.when(paisService.getPaisByCCN3(Mockito.anyString())).thenReturn(null);

        var createClienteDto = new CreateClienteDto();
        createClienteDto.pais = "DOM";

        Assertions.assertThrows(BadRequestException.class, () -> clienteService.createCliente(createClienteDto));
    }

    @Test
    public void testFindById_whenExistingId_returnCliente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(new Cliente());

        long id = 1;

        Assertions.assertNotNull(clienteService.findById(id));
    }

    @Test
    public void testFindById_whenNonExistingId_returnNull() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(null);

        long id = 1;

        Assertions.assertNull(clienteService.findById(id));
    }

    @Test
    public void testFindByCorreo_whenExistingCorreo_returnCliente() {
        var foundClientes = new ArrayList<Cliente>();
        foundClientes.add(new Cliente());
        Mockito.when(clienteRepository.list(Mockito.anyString(), Mockito.anyString())).thenReturn(foundClientes);

        String correo = "johndoe@gmail.com";

        Assertions.assertNotNull(clienteService.findByCorreo(correo));
    }

    @Test
    public void testFindByCorreo_whenNonExistingCorreo_returnNull() {
        Mockito.when(clienteRepository.list(Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<>());

        String correo = "johndoe@gmail.com";

        Assertions.assertNull(clienteService.findByCorreo(correo));
    }

    @Test
    public void testFindAll_returnClientes() {
        var foundClientes = new ArrayList<Cliente>();
        foundClientes.add(new Cliente());
        Mockito.when(clienteRepository.listAll()).thenReturn(foundClientes);

        Assertions.assertNotNull(clienteService.findAll());
        Assertions.assertEquals(clienteService.findAll().size(), foundClientes.size());

    }

    @Test
    public void testCount_returnNumberOfClientes() {
        var foundClientes = new ArrayList<Cliente>();
        foundClientes.add(new Cliente());
        Mockito.when(clienteRepository.count()).thenReturn((long) foundClientes.size());

        Assertions.assertEquals(clienteService.count(), foundClientes.size());

    }

    @Test
    public void testDeleteById_whenExistingId_returnClienteIsDeleted() {
        Mockito.when(clienteRepository.deleteById(Mockito.anyLong())).thenReturn(true);

        long id = 1;

        Assertions.assertTrue(clienteService.deleteClienteById(id));
    }

    @Test
    public void testFindById_whenNonExistingId_returnClienteIsNotDeleted() {
        Mockito.when(clienteRepository.deleteById(Mockito.anyLong())).thenReturn(false);

        long id = 1;

        Assertions.assertFalse(clienteService.deleteClienteById(id));
    }

    @Test
    public void testUpdateCliente_whenValidValues_returnCliente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(new Cliente());
        Mockito.when(paisService.getPaisByCCN3(Mockito.anyString())).thenReturn(new Pais());

        long id = 1;
        var updateClienteDto = new UpdateClienteDto();
        updateClienteDto.pais = "DOM";

        Assertions.assertNotNull(clienteService.updateCliente(id, updateClienteDto));
    }

    @Test
    public void testUpdateCliente_whenNotFoundClient_throwNotFoundException() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(null);
        Mockito.when(paisService.getPaisByCCN3(Mockito.anyString())).thenReturn(new Pais());

        long id = 1;
        var updateClienteDto = new UpdateClienteDto();
        updateClienteDto.pais = "DOM";

        Assertions.assertThrows(NotFoundException.class, () -> clienteService.updateCliente(id, updateClienteDto));
    }

    @Test
    public void testUpdateCliente_whenNotFoundPais_throwBadRequestException() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(new Cliente());
        Mockito.when(paisService.getPaisByCCN3(Mockito.anyString())).thenReturn(null);

        long id = 1;
        var updateClienteDto = new UpdateClienteDto();
        updateClienteDto.pais = "DOM";

        Assertions.assertThrows(BadRequestException.class, () -> clienteService.updateCliente(id, updateClienteDto));
    }

    @Test
    public void testFilter_whenValidArguments_returnClientes() {
        Mockito.when(clienteRepository.list(Mockito.anyString(), Mockito.any(Parameters.class))).thenReturn(new ArrayList<>());

        String pais = "DOM";

        Assertions.assertNotNull(clienteService.filter(pais));
    }

    @Test
    public void testFilter_whenAllArgumentsAreNull_throwBadRequestException() {
        Mockito.when(clienteRepository.list(Mockito.anyString(), Mockito.any(Parameters.class))).thenReturn(new ArrayList<>());

        Assertions.assertThrows(BadRequestException.class, () -> clienteService.filter(null));
    }
}
