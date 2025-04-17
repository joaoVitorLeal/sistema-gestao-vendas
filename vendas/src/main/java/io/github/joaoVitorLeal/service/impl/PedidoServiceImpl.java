package io.github.joaoVitorLeal.service.impl;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import io.github.joaoVitorLeal.domain.entity.ItemPedido;
import io.github.joaoVitorLeal.domain.entity.Pedido;
import io.github.joaoVitorLeal.domain.entity.Produto;
import io.github.joaoVitorLeal.domain.enums.StatusPedido;
import io.github.joaoVitorLeal.domain.repository.ClienteRepository;
import io.github.joaoVitorLeal.domain.repository.ItemPedidoRepository;
import io.github.joaoVitorLeal.domain.repository.PedidoRepository;
import io.github.joaoVitorLeal.domain.repository.ProdutoRepository;
import io.github.joaoVitorLeal.exception.PedidoNaoEncontradoException;
import io.github.joaoVitorLeal.exception.RegraNegocioException;
import io.github.joaoVitorLeal.rest.dto.ItemPedidoRequestDTO;
import io.github.joaoVitorLeal.rest.dto.PedidoRequestDTO;
import io.github.joaoVitorLeal.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getCliente()).orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido status) {
        repository.findById(id).map( p -> {
            p.setStatus(status);
            return repository.save(p);
        }).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não Encontrado!") );
    }


    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoRequestDTO> itensDTO) {
        if(itensDTO.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar o pedido sem itens.");
        }

        return itensDTO.stream() // Stream de DTO
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow( () -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                   ItemPedido itemPedido = new ItemPedido();
                   itemPedido.setQuantidade(dto.getQuantidade());
                   itemPedido.setProduto(produto);
                   itemPedido.setPedido(pedido);
                   return itemPedido;  // Stream de itemPedido
                }).collect(Collectors.toList()); // Retorna uma Stream de lista de itemPedidos
    }
}
