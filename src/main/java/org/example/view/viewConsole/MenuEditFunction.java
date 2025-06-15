package org.example.view;

import org.example.model.entities.UsuariosEntity;
import org.example.control.services.UsuarioService;

import java.util.Optional;
import java.util.Scanner;
import javax.persistence.EntityManager;

public class MenuEditFunction {
    public static void MenuEditarCliente(EntityManager em) {
        UsuarioService usuarioService = new UsuarioService(em);
        Scanner sc = new Scanner(System.in);

        System.out.println("\nMenu de edição de dados");
        System.out.println("    Digite o e-mail do usuário que deseja alterar os dados: ");
        String email = sc.nextLine();
        Optional<UsuariosEntity> usuarioOpt = usuarioService.buscarPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuário com e-mail '" + email + "' não encontrado.");
            return;
        }

        UsuariosEntity usuario = usuarioOpt.get();
        int option = 1;
        while (option != 0) {
            System.out.println("\nDados atuais do usuário:");
            System.out.println("    Nome: " + usuario.getNome());
            System.out.println("    E-mail: " + usuario.getEmail());
            System.out.println("    O e-mail passou pela verificação? " + (usuario.isActive() ? "Sim" : "Não"));
            System.out.println("    Telefone: " + usuario.getTelefone());
            System.out.println("    Senha (com Hash): " + usuario.getSenha());
            System.out.println("    Usuário é admin? " + (usuario.isAdmin() ? "Sim" : "Não"));

            System.out.println("\nO que deseja editar?");
            System.out.println("    1. Nome");
            System.out.println("    2. Número de telefone");
            System.out.println("    3. Senha");
            System.out.println("    4. Alterar para admin/usuário comum");
            System.out.println("    5. Alterar usuário para ativo/inativo (Verificação de e-mail)");
            System.out.println("    6. Excluir cliente");
            System.out.println("    0. Retornar ao menu admin");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    usuarioService.editarNome(usuario, sc);
                break;
                case 2:
                    usuarioService.editarTelefone(usuario, sc);
                break;
                case 3:
                    usuarioService.editarSenha(usuario, sc);
                break;
                case 4:
                    usuarioService.editarIsAdmin(usuario);
                break;
                case 5:
                    usuarioService.editarIsActive(usuario);
                    break;
                case 6:
                    System.out.println("Tem certeza que deseja excluir este usuário? (s/n)");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("s")) {
                        usuarioService.excluirUsuario(usuario);
                        return;
                    }
                break;
                case 0:
                    System.out.println("Retornando...");
                break;
                default:
                    System.out.println("ERRO: opção inválida");
                break;
            }
        }
    }
}
