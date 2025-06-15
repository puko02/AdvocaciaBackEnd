package org.example.view.viewGUI;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.example.model.UsuariosEntity;
import org.mindrot.jbcrypt.BCrypt;

public class MenuLoginSwing extends JFrame {

    private static JTextField userField;
    private static JPasswordField passwordField;
    private final UsuariosEntity usuariosEntity;

        public MenuLoginSwing(EntityManager em) {
            this.usuariosEntity = new UsuariosEntity();

            setSize(500, 300);
            getContentPane().setLayout(null);

            JPanel titlePanel = new JPanel();
            titlePanel.setBounds(0, 10, 486, 33);
            getContentPane().add(titlePanel);

            JLabel titleLabel = new JLabel("Menu Login");
            titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            titlePanel.add(titleLabel);

            JPanel loginPanel = new JPanel();
            loginPanel.setBounds(10, 72, 466, 89);
            getContentPane().add(loginPanel);
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

            JLabel statusLabel = new JLabel("");
            loginPanel.add(statusLabel);

            JButton btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
            loginPanel.add(btnLogin);

            JPanel panel = new JPanel();
            panel.setBounds(0, 220, 486, 33);
            getContentPane().add(panel);

            JButton btnRetornar = new JButton("Retornar");
            btnRetornar.setFont(new Font("Tahoma", Font.BOLD, 13));
            btnRetornar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                }
            });
            panel.add(btnRetornar);

            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = userField.getText();
                    String senha = new String(passwordField.getPassword());

                    if (email.isEmpty() || senha.isEmpty()) {
                        statusLabel.setText("Preencha todos os campos.");
                        return;
                    }

                    try {
                        String jpql = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
                        TypedQuery<UsuariosEntity> query = em.createQuery(jpql, UsuariosEntity.class);
                        query.setParameter("email", email);

                        UsuariosEntity usuario = query.getSingleResult();

                        if (!usuario.isAdmin()) {
                            statusLabel.setText("Sem permissão administrativa.");
                            return;
                        }

                        if (BCrypt.checkpw(senha, usuario.getSenha())) {
                            statusLabel.setForeground(Color.GREEN);
                            statusLabel.setText("Acesso permitido!");

                            // Abrir Menu Admin
                            dispose(); // Fecha a janela de login
                            MenuAdmin.mostrar(em);

                        } else {
                            statusLabel.setText("Senha incorreta.");
                        }

                    } catch (Exception ex) {
                        statusLabel.setText("E-mail não encontrado.");
                    }
                }
            });
        }

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            MenuLoginSwing frmMenuLogin = new MenuLoginSwing(em);
            frmMenuLogin.setVisible(true);
        });
    }

}
