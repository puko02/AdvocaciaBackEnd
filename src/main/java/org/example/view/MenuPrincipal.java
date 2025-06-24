package org.example.view;

import org.example.control.services.MenuConsultasController;
import org.example.control.services.MenuLoginController;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal(EntityManager em) {
        setTitle("Menu Principal");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color amareloPastel = new Color(255, 249, 196);

        // Painel principal com GridLayout e borda
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(amareloPastel);

        JLabel lblTitulo = new JLabel("⚖ Bem-vindo ao Sistema de Agendamento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(102, 51, 0)); // marrom suave

        // Botões
        JButton btnLogin = new JButton("Fazer Login ADM");
        JButton btnAgendamento = new JButton("Fazer Agendamento");
        JButton btnConsultas = new JButton("Ver sua Consulta");
        JButton btnSair = new JButton("Sair");

        JButton[] botoes = {btnLogin, btnAgendamento, btnConsultas, btnSair};
        for (JButton botao : botoes) {
            botao.setFont(new Font("SansSerif", Font.PLAIN, 14));
            botao.setBackground(Color.WHITE);
            botao.setFocusPainted(false);
        }

        // Adicionando componentes
        panel.add(lblTitulo);
        panel.add(btnLogin);
        panel.add(btnAgendamento);
        panel.add(btnConsultas);
        panel.add(btnSair);

        add(panel);

        // Ações
        btnLogin.addActionListener(e -> {
            MenuLoginSwing.abrirTela(new MenuLoginController(em));
        });

        btnAgendamento.addActionListener(e -> {
            UsuariosEntity usuario = AvisoConsultaEmailSwing.solicitarEmail(em);
            if (usuario == null) return;

            if (usuario.getNome().isEmpty() || usuario.getTelefone().isEmpty()) {
                usuario = CadastroSwing.RealizarCadastro(em, usuario);
            }
            if (usuario == null) return;

            if (!usuario.isActive()) {
                JOptionPane.showMessageDialog(this, "Enviando token de validação para seu e-mail. Aguarde...", "Validação de e-mail", JOptionPane.INFORMATION_MESSAGE);
                VerificacaoEmailSwing.mostrar(em, usuario);
            } else {
                AgendamentoSwing.mostrar(em, usuario);
            }
        });

        btnConsultas.addActionListener(e -> {
            MenuConsultasSwing.abrirTela(new MenuConsultasController(em));
        });

        btnSair.addActionListener(e -> {
            em.close();
            dispose();
            System.exit(0);
        });
    }


    public static void mostrar(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal(em).setVisible(true);
        });
    }
}
