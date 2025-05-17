package org.example.functions;
import org.example.models.UsuariosEntity;
import org.example.services.UsuarioService;
import org.mindrot.jbcrypt.BCrypt;

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
        int numero;
        Optional<UsuariosEntity> usuarioOpt = usuarioService.buscarPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuário com e-mail '" + email + "' não encontrado.");
            return;
        }

        UsuariosEntity usuario = usuarioOpt.get();
        System.out.println("Usuário encontrado:");

        int option = 1;
        while (option != 0) {
            System.out.println("    Nome: " + usuario.getNome());
            System.out.println("    E-mail: " + usuario.getEmail());
            System.out.println("    Telefone: " + usuario.getTelefone());
            System.out.println("    Senha (com Hash): " + usuario.getSenha());
            System.out.println("    Usuário é admin? " + usuario.isAdmin());

            System.out.println("\nO que deseja editar do usuário do email: " + usuario.getEmail());
            System.out.println("    1. Nome");
            System.out.println("    2. Número de telefone");
            System.out.println("    3. Senha");
            System.out.println("    4. Alterar para admin/usuário comum");
            System.out.println("    0. Retornar ao menu admin");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    EditNome(usuario, em, sc);
                    break;
                case 2:
                    EditTelefone(usuario, em, sc);
                    break;
                case 3:
                    EditSenha(usuario, em, sc);
                    break;
                case 4:
                    EditIsAdmin(usuario, em);
                    break;
                case 0:
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println("ERRO: opção invalida");
                    break;
            }
        }
    }
    public static void EditNome(UsuariosEntity usuario, EntityManager em, Scanner sc){
        System.out.println("Deseja alterar para qual nome?");
        String nomeNovo = sc.nextLine();
        if (nomeNovo.length() > 200){
            System.out.println("ERRO: O nome não pode ter mais de 200 caractéres\n");
            return;
        }

        em.getTransaction().begin();
        usuario.setNome(nomeNovo);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Nome alterado para: " + nomeNovo + "\n\n");
    }
    public static void EditTelefone(UsuariosEntity usuario, EntityManager em, Scanner sc){
        System.out.println("Deseja altera para qual número de telefone? (deve ter 11 dígitos)");
        String telefoneNovo = sc.nextLine();
        if (telefoneNovo.length() > 11 || telefoneNovo.length() < 10){
            System.out.println("ERRO: O número de telefone deve ter 11 caractéres\n");
            return;
        }

        em.getTransaction().begin();
        usuario.setTelefone(telefoneNovo);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("número alterado para:  " + usuario.getTelefone() + "\n");
    }

    public static void EditSenha(UsuariosEntity usuario, EntityManager em, Scanner sc){
        System.out.println("Deseja alterar para qual senha?");
        String senhaNova = sc.nextLine();
        if (BCrypt.checkpw(senhaNova,usuario.getSenha())){
            System.out.println("ERRO: A senha atual não pode ser igual a senha que será alterada!\n");
            return;
        }
        String senhaHashed = BCrypt.hashpw(senhaNova,BCrypt.gensalt());

        em.getTransaction().begin();
        usuario.setSenha(senhaHashed);
        em.merge(usuario);
        em.getTransaction().commit();

        System.out.println("Senha alterada para: " + senhaNova);
        System.out.println("Senha com hashing: " + senhaHashed + "\n");
    }

    public static void EditIsAdmin(UsuariosEntity usuario, EntityManager em){
        if (usuario.isAdmin()) {
            em.getTransaction().begin();
            usuario.setAdmin(false);
            em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("Alterado de admin para usuário comum!\n");
        }
        else {
            em.getTransaction().begin();
            usuario.setAdmin(true);
            em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("Alterado de usuário comum para admin!\n");
        }
    }
}