package org.example.view;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import org.example.model.UsuariosEntity;
import org.mindrot.jbcrypt.BCrypt;

public class MenuLoginFunction {
    private static JTextField userField;
    private static JPasswordField passwordField;

    public static void Menulogin(EntityManager em, Scanner sc) {

            JFrame frmMenuLogin = new JFrame();
            frmMenuLogin.setSize(500, 300);
            frmMenuLogin.getContentPane().setLayout(null);

            JPanel titlePanel = new JPanel();
            titlePanel.setBounds(0, 10, 486, 33);
            frmMenuLogin.getContentPane().add(titlePanel);

            JLabel titleLabel = new JLabel("Menu Login");
            titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            titlePanel.add(titleLabel);

            JPanel loginPanel = new JPanel();
            loginPanel.setBounds(10, 72, 466, 89);
            frmMenuLogin.getContentPane().add(loginPanel);
            loginPanel.setLayout(new GridLayout(0, 2, 0, 0));

            JLabel userLabel = new JLabel("Email do Usuário:");
            userLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
            loginPanel.add(userLabel);

            userField = new JTextField();
            userLabel.setLabelFor(userField);
            userField.setFont(new Font("Tahoma", Font.PLAIN, 12));
            loginPanel.add(userField);
            userField.setColumns(10);

            JLabel passwordLabel = new JLabel("Senha do Usuário: ");
            passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
            loginPanel.add(passwordLabel);

            passwordField = new JPasswordField();
            passwordLabel.setLabelFor(passwordField);
            passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
            loginPanel.add(passwordField);

            JLabel blankLabel = new JLabel("");
            loginPanel.add(blankLabel);

            JButton btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
            loginPanel.add(btnLogin);

            JPanel panel = new JPanel();
            panel.setBounds(0, 220, 486, 33);
            frmMenuLogin.getContentPane().add(panel);

            JButton btnNewButton = new JButton("Retornar");
            btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
            panel.add(btnNewButton);

            frmMenuLogin.setVisible(true);

        System.out.println("Menu Login\nDigite o e-mail: ");
        String email = sc.nextLine();

        String prefixo = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
        TypedQuery<UsuariosEntity> query = em.createQuery(prefixo, UsuariosEntity.class);
        query.setParameter("email", email);

        UsuariosEntity usuario = null;
        try {
            usuario = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("E-mail não encontrado. Pressione ENTER para retornar ao menu principal.");
            sc.nextLine();
            return;
        }

        if (!usuario.isAdmin()){
            System.out.println("Usuário não tem permissões administrativas. Pressione ENTER para retornar ao menu principal");
            sc.nextLine();
            return;
        }

        System.out.println("Digite a senha: ");
        if (BCrypt.checkpw(sc.nextLine(), usuario.getSenha())) {
            System.out.println("Acesso permitido!\n\n");
            MenuAdmin.menuAdministrador(em);
        } else {
            System.out.println("Senha incorreta. Pressione ENTER para retornar ao menu principal.");
            sc.nextLine();
        }
    }
}
