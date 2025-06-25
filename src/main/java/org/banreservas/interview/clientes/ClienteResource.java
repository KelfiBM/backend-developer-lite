package org.banreservas.interview.clientes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.banreservas.interview.clientes.dtos.CreateClienteDto;
import org.banreservas.interview.clientes.dtos.UpdateClienteDto;

import java.util.List;

@ApplicationScoped
@Path("/clientes")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Cliente get(@PathParam("id") Long id) {
        var cliente = clienteService.findById(id);

        if (cliente == null) {
            throw new NotFoundException("Cliente no encontrado");
        }
        return cliente;
    }

    @GET
    @Produces("application/json")
    public List<Cliente> list() {
        return clienteService.findAll();
    }

    @GET
    @Path("/count")
    public Long count() {
        return clienteService.count();
    }

    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Cliente add(@Valid CreateClienteDto createClienteDto) {
        return clienteService.createCliente(createClienteDto);
    }

    @Transactional
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Cliente update(@PathParam("id") Long id, @Valid UpdateClienteDto updateClienteDto) {
        if (id == 0) {
            throw new BadRequestException("ID invalido");
        }

        return clienteService.updateCliente(id, updateClienteDto);
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        if (id == 0) {
            throw new BadRequestException("ID invalido");
        }

        if (!clienteService.deleteClienteById(id)) {
            throw new NotFoundException("Cliente no encontrado");
        }

    }

    @GET
    @Path("/search")
    @Produces("application/json")
    public List<Cliente> findByPais(@QueryParam("pais") String pais) {
        return clienteService.filter(pais);
    }

}
