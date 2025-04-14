package io.github.joaoVitorLeal;

import io.github.joaoVitorLeal.domain.entity.Cliente;
import io.github.joaoVitorLeal.domain.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
        return args -> {
            System.out.println("Salvando clientes.");
            clienteRepository.salvar(new Cliente("João"));
            clienteRepository.salvar(new Cliente("Manuela"));

            System.out.println("Buscando clientes");
            List<Cliente> todosClientes =  clienteRepository.obterClientes();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " Atualizado");
                clienteRepository.atualizar(c);
            });

            // Exibindo clientes atualizados
            todosClientes = clienteRepository.obterClientes();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando cliente");
            clienteRepository.buscarPorNome("nue").forEach(System.out::println);

//            System.out.println("Deletando clientes");
//            clienteRepository.obterClientes().forEach(clienteRepository::deletar);

            todosClientes =  clienteRepository.obterClientes();
            if (todosClientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}

