package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import io.github.joaoVitorLeal.domain.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {

    private static final String NOT_FOUND_MESSAGE = "Cliente não encontrado!";

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Não foi encontrado cliente com o ID informado")
    })
    public Cliente obterClientePorId( @PathVariable Integer id ) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente salvar( @RequestBody @Valid Cliente cliente ) {
        return repository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletar cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente deletado"),
            @ApiResponse(code = 404, message = "Não foi encontrado cliente com o ID informado")
    })
    public void deletar (@PathVariable Integer id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
        repository.delete(cliente);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado"),
            @ApiResponse(code = 404, message = "Não foi encontrado cliente com o ID informado")
    })
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
        repository.findById(id)
                .map( clienteEncontrado -> {
                    cliente.setId(clienteEncontrado.getId());
                    repository.save(cliente);
                    return cliente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @GetMapping
    @ResponseStatus(OK)
    @ApiOperation("Buscar clientes por filtros")
    @ApiResponse(code = 200, message = "")
    public List<Cliente> buscaPorFiltros(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher
                        .CONTAINING
                ); // forma como deve buscar os valores String


        Example<Cliente> example = Example.of(filtro, matcher); // obter as propriedades que estão populadas e criar um obj example
        return  repository.findAll(example);
    }
}
