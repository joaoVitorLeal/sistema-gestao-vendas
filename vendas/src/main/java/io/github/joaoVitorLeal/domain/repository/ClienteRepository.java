package io.github.joaoVitorLeal.domain.repository;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private EntityManager entityManager; // Realiza todas as operações na base de dados com as entidades

    @Transactional // Abre uma transação para realizar operações com o EntityManager
    public Cliente salvar(Cliente cliente) {
        // antes de uma entidade ser salva ele se encontra no estado 'Transiente', após a persistência, 'Manager' (gerenciável)
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar (Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if(!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente); // o merge serve para sincronizar o cliente com o EntityManager
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true) // transação apenas de leitura, JPA otimiza a consulta
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "select c from Cliente c where c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterClientes() {
        return entityManager.createQuery("from Cliente", Cliente.class)
                .getResultList();
    }
}
