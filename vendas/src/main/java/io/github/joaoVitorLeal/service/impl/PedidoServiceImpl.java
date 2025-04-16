package io.github.joaoVitorLeal.service.impl;

import io.github.joaoVitorLeal.domain.repository.PedidoRepository;
import io.github.joaoVitorLeal.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;

    public PedidoServiceImpl(PedidoRepository repository) {
        this.repository = repository;
    }
}
