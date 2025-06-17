package org.example.view.viewGUI;

import org.example.control.services.MenuLoginController;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuLoginSwing extends JFrame {

    private JTextField userField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private final MenuLoginController controller;

    public MenuLoginSwing(MenuLoginController controller) {
        this.controller = controller;

        setTitle("Menu Login");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel passwordLabel = new JLabel("Senha do Usuário:");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordLabel.setLabelFor(passwordField);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        loginPanel.add(passwordField);

        statusLabel = new JLabel("");
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

        btnLogin.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String email = userField.getText().trim();
        String senha = new String(passwordField.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Preencha todos os campos.");
            return;
        }

        String resultado = controller.validarLogin(email, senha);

        if (resultado.equals("sucesso")) {
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("Acesso permitido!");
            dispose();
            controller.abrirMenuAdmin();
        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(resultado);
        }
    }

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            MenuLoginSwing frmMenuLogin = new MenuLoginSwing(em);
            frmMenuLogin.setVisible(true);
        });
    }
}