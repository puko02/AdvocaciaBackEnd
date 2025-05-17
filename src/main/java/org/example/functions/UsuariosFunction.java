package org.example.functions;

import org.example.models.AnotacaoEntity;
import org.example.models.UsuariosEntity;
import org.example.repositories.AnotacaoRepository;
import org.example.repositories.IAnotacaoRepository;
import org.example.services.UsuarioService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class UsuariosFunction {

    public static void menuUser(EntityManager em) {
        UsuarioService usuariosService = new UsuarioService(em);
        IAnotacaoRepository anotacaoRepo = new AnotacaoRepository(em);
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1 - Listar Clientes");
            System.out.println("2 - Adicionar Nota a um Cliente");
            System.out.println("3 - Ver Notas de um Cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    List<UsuariosEntity> clientes = usuariosService.listarClientes();
                    clientes.forEach(cliente -> {
                        System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome());
                    });
                }
                case 2 -> {
                    System.out.print("Informe o ID do cliente: ");
                    Long idCliente = sc.nextLong();
                    sc.nextLine();
                    System.out.print("Digite a nota: ");
                    String conteudo = sc.nextLine();
                    anotacaoRepo.adicionarAnotacao(idCliente, conteudo);
                    System.out.println("Nota adicionada com sucesso!");
                }
                case 3 -> {
                    System.out.print("Informe o ID do cliente: ");
                    Long idBusca = sc.nextLong();
                    sc.nextLine();
                    List<AnotacaoEntity> notas = anotacaoRepo.buscarAnotacoesPorCliente(idBusca);
                    if (notas.isEmpty()) {
                        System.out.println("Nenhuma nota encontrada.");
                    } else {
                        notas.forEach(n -> {
                            System.out.println("\nID da nota: " + n.getId() +
                                    "\nConteúdo: " + n.getNota() +
                                    "\nCriado em: " + n.getDataCriacao());
                        });
                    }
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }
}
