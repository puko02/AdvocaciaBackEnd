package org.example.view;

import org.example.control.services.MenuConsultasController;
import org.example.control.services.MenuLoginController;
import org.example.model.entities.UsuariosEntity;
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
            }
            else{
                AgendamentoSwing.mostrar(em,usuario);
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