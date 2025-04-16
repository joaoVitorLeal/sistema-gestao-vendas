package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import io.github.joaoVitorLeal.domain.repository.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClientePorId( @PathVariable Integer id ) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);

        if (clienteEncontrado.isPresent()) {
            return ResponseEntity.ok(clienteEncontrado.get()); // Equivalente a: ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(clienteEncontrado.get(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity salvar( @RequestBody Cliente cliente ) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete (@PathVariable Integer id) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);

        if (!clienteEncontrado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.delete(clienteEncontrado.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity atualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteRepository.findById(id)
                .map( clienteEncontrado -> {
                    cliente.setId(clienteEncontrado.getId());
                    clienteRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    public ResponseEntity buscaPorFiltros(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher
                        .StringMatcher
                        .CONTAINING
                ); // forma como deve buscar os valores String


        Example example = Example.of(filtro, matcher); // obtém as propriedades que estão populados e criar um obj example
        List<Cliente> clientes = clienteRepository.findAll(example);

        return ResponseEntity.ok(clientes);
    }

//    @RequestMapping(
//            value = "/hello/{nome}",
//            method = RequestMethod.GET,
//            consumes = { "application/json", "application/xml" }, // Formato de dados consumidos pela aplicação nas requisições vindas dos clientes
//            produces = { "application/json", "application/xml" } // Formatos de dados retornados nas respostas produzidas por esta aplicação
//    )
}
