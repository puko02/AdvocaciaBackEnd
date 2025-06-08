package org.example.view;

import javax.persistence.EntityManager;
import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal(EntityManager em) {

        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de botões
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnLogin = new JButton("Fazer Login ADM");
        JButton btnAgendamento = new JButton("Fazer Agendamento");
        JButton btnConsultas = new JButton("Ver sua Consulta");
        JButton btnSair = new JButton("Sair");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnLogin);
        panel.add(btnAgendamento);
        panel.add(btnConsultas);
        panel.add(btnSair);

        add(panel);

        // Ações dos botões
        btnLogin.addActionListener(e -> {
            MenuLoginSwing.abrirTela(em);
        });

        btnAgendamento.addActionListener(e -> {
            Agendamento.fazerAgendamento(em);
        });

        btnConsultas.addActionListener(e -> {
            MenuConsultasSwing.abrirTela(em);
        });

        btnSair.addActionListener(e -> {
            em.close(); // Fecha o EntityManager
            dispose();// Fecha a janela Swing
            System.exit(0);
        });
    }

    public static void mostrar(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal(em).setVisible(true);
        });

    }

}