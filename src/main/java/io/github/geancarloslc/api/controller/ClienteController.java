package io.github.geancarloslc.api.controller;

import io.github.geancarloslc.domain.entity.Cliente;
import io.github.geancarloslc.domain.repository.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
//RestController, Anotação especifica para RestFul não havendo mais necessidade da @Reponsebody nos metodos abaixo
@RequestMapping("/api/clientes/")
public class ClienteController {

    private ClienteDAO clienteDAO;

    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @GetMapping("getClienteById/{id}") //Get para obter dados
    public Cliente getClienteById(@PathVariable("id") Integer id){

        return clienteDAO
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    @PostMapping("salvarCliente")
    @ResponseStatus(HttpStatus.CREATED) //201
    public Cliente salvarCliente(@Valid @RequestBody Cliente cliente ){

        return clienteDAO.save(cliente);
    }

    @DeleteMapping("deletarCliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deletarCliente(@PathVariable Integer id){
        clienteDAO.findById(id)
                .map( cliente -> {
                    clienteDAO.delete(cliente);
                    return Void.TYPE;
                } )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));

    }

    @PutMapping("atualizarCliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    //Atualizar integralmente um recurso no servidor
    public void atualizarCliente(@Valid @PathVariable Integer id, @RequestBody Cliente cliente){

        clienteDAO.findById(id)
                .map(clienteExistente ->{
                    cliente.setId(clienteExistente.getId());
                    clienteDAO.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping("filtrarClientes")
    public List<Cliente> filtrarClientes (Cliente filtroCliente){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );
        //ExampleMatcher.StringMatcher.CONTAINING, similar ao like do SQL
        Example exampleCliente = Example.of(filtroCliente, exampleMatcher);
        return clienteDAO.findAll(exampleCliente);
    }

}
