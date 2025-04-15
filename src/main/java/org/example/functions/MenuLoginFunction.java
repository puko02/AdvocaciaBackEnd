package org.example.functions;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Scanner;
import org.example.models.UsuariosEntity;

public class MenuLoginFunction {
    public static void Menulogin(EntityManager em, Scanner sc) {
        System.out.println("Menu Login\nDigite o e-mail: ");
        String email = sc.nextLine();
        System.out.println("Digite a senha: ");
        String senha = sc.nextLine();

        String prefixo = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
        TypedQuery<UsuariosEntity> query = em.createQuery(prefixo, UsuariosEntity.class);
        query.setParameter("email", email);

        UsuariosEntity usuario = null;
        try {
            usuario = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("E-mail não encontrado. Pressione ENTER para retornar ao menu principal.");
            sc.next();
            return;
        }

        if (usuario.getSenha().equals(senha)) {
            System.out.println("Acesso permitido!\n\n");
            MenuAdmin.menuAdministrador();
        } else {
            System.out.println("Senha incorreta. Pressione ENTER para retornar ao menu principal.");
            sc.next();
        }
    }
}
