package org.banreservas.interview.clientes;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.banreservas.interview.clientes.dtos.*;
import org.banreservas.interview.clientes.filters.ClienteFilter;
import org.banreservas.interview.paises.PaisService;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PaisService paisService;

    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente findByCorreo(String correo) {
        var clientes = clienteRepository.list("correo", correo);
        if (clientes.isEmpty()) {
            return null;
        }
        return clientes.getFirst();
    }

    public List<Cliente> findAll() {
        return clienteRepository.listAll().stream().toList();
    }

    public long count() {
        return clienteRepository.count();
    }

    public Cliente createCliente(CreateClienteDto createClienteDto) {
        var pais = paisService.getPaisByCCN3(createClienteDto.pais);
        if (pais == null) {
            throw new BadRequestException("Pais no encontrado");
        }
        Cliente cliente = new Cliente();
        cliente.pais = pais;
        cliente.correo = createClienteDto.correo;
        cliente.direccion = createClienteDto.direccion;
        cliente.telefono = createClienteDto.telefono;
        cliente.primerNombre = createClienteDto.primerNombre;
        cliente.segundoNombre = createClienteDto.segundoNombre;
        cliente.primerApellido = createClienteDto.primerApellido;
        cliente.segundoApellido = createClienteDto.segundoApellido;
        clienteRepository.persist(cliente);
        return cliente;
    }

    public Cliente updateCliente(Long id, UpdateClienteDto updateClienteDto) {
        var cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente no encontrado");
        }
        cliente.telefono = updateClienteDto.telefono == null ? cliente.telefono : updateClienteDto.telefono;
        cliente.direccion = updateClienteDto.direccion == null ? cliente.direccion : updateClienteDto.direccion;
        cliente.correo = updateClienteDto.correo == null ? cliente.correo : updateClienteDto.correo;
        cliente.pais = updateClienteDto.pais == null ? cliente.pais : paisService.getPaisByCCN3(updateClienteDto.pais);
        return cliente;
    }

    public boolean deleteClienteById(Long id) {
        return clienteRepository.deleteById(id);
    }

    public List<Cliente> filter(String pais) {
        var filter = new ClienteFilter().paisCodeCCA3(pais);

        if (filter.buildParameters().isEmpty()) {
            throw new BadRequestException("Debe proveer almenos un parametro");
        }

        var parameters = filter.buildParameters();
        Parameters queryParameters = null;

        for (var parameterKey : parameters.keySet()) {
            queryParameters = queryParameters == null ? Parameters.with(parameterKey, parameters.get(parameterKey)) : queryParameters.and(parameterKey, parameters.get(parameterKey));
        }

        return clienteRepository.list(filter.buildQuery(), queryParameters);
    }
}
