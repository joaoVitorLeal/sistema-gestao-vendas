package io.github.joaoVitorLeal.domain.repository;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(" select c from Cliente c left join fetch c.pedidos p where c.id = :id ") // LEFT JOIN: Trará um cliente independente dele ter pedido ou não.
    Cliente findClienteFetchPedidos(Integer id);

    ///////////////////////////////////////////////////////////////////////////////////
    List<Cliente> getByNomeLike(String nome);

    List<Cliente> findByNomeLikeOrIdOrderById(String nome, String id);

    Cliente findOneByCpf(String cpf); // ( dados únicos como CPF)

    boolean existsByNome(String nome);
    /// QueryMethod — HQL
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> HQLEncontrarPorNome(@Param("nome") String nome);

    @Query(" delete from Cliente c where c.nome like :nome ")
    @Modifying // Necessário, para @Query de deleção e atualização
    void deleteByNome(String nome);
    /// QueryMethod — SQL nativo
    @Query(value = "select * from Cliente c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> SQLEncontrarPorNome(@Param("nome") String nome);

}
