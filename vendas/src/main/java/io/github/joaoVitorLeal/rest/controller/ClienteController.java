package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import io.github.joaoVitorLeal.domain.repository.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private static final String NOT_FOUND_MESSAGE = "Cliente não encontrado!";

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente obterClientePorId( @PathVariable Integer id ) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar( @RequestBody Cliente cliente ) {
        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        repository.delete(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        repository.findById(id)
                .map( clienteEncontrado -> {
                    cliente.setId(clienteEncontrado.getId());
                    repository.save(cliente);
                    return cliente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> buscaPorFiltros(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher
                        .CONTAINING
                ); // forma como deve buscar os valores String


        Example example = Example.of(filtro, matcher); // obtém as propriedades que estão populados e criar um obj example
        return  repository.findAll(example);
    }

//    @RequestMapping(
//            value = "/hello/{nome}",
//            method = RequestMethod.GET,
//            consumes = { "application/json", "application/xml" }, // Formato de dados consumidos pela aplicação nas requisições vindas dos clientes
//            produces = { "application/json", "application/xml" } // Formatos de dados retornados nas respostas produzidas por esta aplicação
//    )
}
