package org.example.functions;

import org.example.models.AnotacaoEntity;
import org.example.models.UsuariosEntity;
import org.example.repositories.AnotacaoRepository;
import org.example.repositories.IAnotacaoRepository;
import org.example.repositories.UsuariosRepository;
import org.example.repositories.IUsuariosRepository;

import java.util.List;
import java.util.Scanner;

public class UsuariosFunction {

    public static void main(String[] args) {
        IUsuariosRepository usuariosRepo = new UsuariosRepository();
        IAnotacaoRepository anotacaoRepo = new AnotacaoRepository();
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
                case 1:
                    List<UsuariosEntity> clientes = usuariosRepo.listarClientes();
                    clientes.forEach(cliente -> {
                        System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome());
                    });
                    break;
                case 2:
                    System.out.print("Informe o ID do cliente: ");
                    Long idCliente = sc.nextLong();
                    sc.nextLine();
                    System.out.print("Digite a nota: ");
                    String conteudo = sc.nextLine();
                    anotacaoRepo.adicionarAnotacao(idCliente, conteudo);
                    System.out.println("Nota adicionada com sucesso!");
                    break;
                case 3:
                    System.out.print("Informe o ID do cliente: ");
                    Long idBusca = sc.nextLong();
                    sc.nextLine();
                    List<AnotacaoEntity> notas = anotacaoRepo.buscarAnotacoesPorCliente(idBusca);
                    if (notas.isEmpty()) {
                        System.out.println("Nenhuma nota encontrada.");
                    } else {
                        notas.forEach(n -> {
                            System.out.println("ID da nota: " + n.getId() + " | Conteúdo: " + n.getNota() + " | Criado em: " + n.getDataCriacao());
                        });
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }
}

