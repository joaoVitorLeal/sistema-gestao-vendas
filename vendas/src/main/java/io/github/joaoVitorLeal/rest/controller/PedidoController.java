package io.github.joaoVitorLeal.rest.controller;

import io.github.joaoVitorLeal.domain.entity.Pedido;
import io.github.joaoVitorLeal.domain.enums.StatusPedido;
import io.github.joaoVitorLeal.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.joaoVitorLeal.rest.mapper.PedidoMapper;
import io.github.joaoVitorLeal.rest.dto.PedidoRequestDTO;
import io.github.joaoVitorLeal.rest.dto.PedidoResponseDTO;
import io.github.joaoVitorLeal.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;


    public PedidoController(PedidoService service, PedidoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer salvar(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public PedidoResponseDTO obterPorId (@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map( p -> mapper.converterParaDTO(p)
                ).orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!"));
    }

    @PatchMapping("{id}") // utilizado para atualizar um campo ou parte específica da entidade
    @ResponseStatus(NO_CONTENT)
    public void atualizarStatus( @PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto ) {
        String novoStatus = dto.getNovoStatus();
        service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }

}
