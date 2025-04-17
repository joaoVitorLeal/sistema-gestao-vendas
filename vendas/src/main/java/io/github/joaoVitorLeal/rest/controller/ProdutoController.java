package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Produto;
import io.github.joaoVitorLeal.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private static final String NOT_FOUND_MESSAGE = "Produto não encontrado!";

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Produto obterProdutoPorId (@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE) );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void salvar(@RequestBody @Valid Produto produto) {
        repository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository.findById(id)
                .map( p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE) );
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void atualizar (@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        repository.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    return repository.save(produto);
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Produto> buscaPorFiltros(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Produto> example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }
}


