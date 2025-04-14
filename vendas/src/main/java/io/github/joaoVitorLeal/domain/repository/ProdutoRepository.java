package io.github.joaoVitorLeal.domain.repository;

import io.github.joaoVitorLeal.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
