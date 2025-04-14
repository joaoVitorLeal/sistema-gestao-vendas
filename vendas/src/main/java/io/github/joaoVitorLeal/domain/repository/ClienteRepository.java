package io.github.joaoVitorLeal.domain.repository;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    private static String INSERT = "INSERT INTO cliente (nome) VALUES (?) ";
    private static String SELECT_ALL = "SELECT * FROM cliente ";
    private static String UPDATE = "UPDATE cliente SET nome = ? WHERE id = ? ";
    private static String DELETE = "DELETE FROM cliente WHERE id = ? ";

    @Autowired
    JdbcTemplate jdbcTemplate; // Classe que possui a conexão já configurada e possui métodos para realizar operações no banco de dados

    public Cliente salvar(Cliente cliente) {
        jdbcTemplate.update(INSERT,  new Object[]{cliente.getNome()}); // Passamos a query SQL e criado um array de Object, onde cada posição é um valor que será substituído nos placeholders (?) da query SQL.
        return cliente;
    }

    public Cliente atualizar (Cliente cliente) {
        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(), cliente.getId(),
        });
        return cliente;
    }

    public void deletar(Cliente cliente) {
        deletar(cliente.getId());
    }

    public void deletar(Integer id) {
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public List<Cliente> buscarPorNome(String nome) {
        return jdbcTemplate.query(
                SELECT_ALL.concat("WHERE nome LIKE ? "),
                new Object[]{"%" + nome + "%"},
                getClienteRowMapper()
        );
    }

    public List<Cliente> obterClientes() {
        return jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
    }

    private static RowMapper<Cliente> getClienteRowMapper() {
        // RowMapper (mapeia o resultado de consulta do banco de dados para uma classe) ///
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id"); // obtém o Int da coluna 'id'
                String nome = resultSet.getString("nome"); // obtém a String da coluna 'nome'
                return new Cliente(id, nome);
            }
        };
    }
}
