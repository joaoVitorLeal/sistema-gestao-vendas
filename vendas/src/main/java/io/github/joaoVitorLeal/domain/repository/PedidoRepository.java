package io.github.joaoVitorLeal.domain.repository;

import io.github.joaoVitorLeal.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
