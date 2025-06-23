package org.example.view;

import org.example.control.services.DisponibilidadeController;
import org.example.model.entities.UsuariosEntity;
import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class MenuAdmin extends JFrame {

    public MenuAdmin(EntityManager em) {
        setTitle("Menu do Administrador");
        setSize(400, 400);
        setLocationRelativeTo(null); // Centraliza na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));
        
        // Componentes
        JLabel titulo = new JLabel("Bem-vindo ao menu de Administrador", SwingConstants.CENTER);
        JButton btnEditarHorario = new JButton("1 - Editar horário de atendimento");
        JButton btnNotasClientes = new JButton("2 - Notas de clientes");
        JButton btnVerAgendamentos = new JButton("3 - Verificar agendamentos");
        JButton btnEditarCliente = new JButton("4 - Editar dados de cliente");
        JButton btnSair = new JButton("0 - Sair");

        // Ações dos botões
        btnEditarHorario.addActionListener(e -> DisponibilidadeSwing.abrirTela(new DisponibilidadeController(em)));
        btnNotasClientes.addActionListener(e -> UsuariosSwing.abrirTela(em));
        btnVerAgendamentos.addActionListener(e -> VisuAgendSwing.abrirTela());
        btnEditarCliente.addActionListener(e -> {
            UsuariosEntity usuario = AvisoConsultaEmailSwing.solicitarEmail(em);
            if (usuario != null){
                MenuEditSwing.mostrar(em, usuario);
            }
        });
        btnSair.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Retornando...");
            dispose(); // Fecha a janela
        });

        // Adiciona componentes
        add(titulo);
        add(btnEditarHorario);
        add(btnNotasClientes);
        add(btnVerAgendamentos);
        add(btnEditarCliente);
        add(btnSair);

    }

    public static void mostrar(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            MenuAdmin menu = new MenuAdmin(em);
            menu.setVisible(true);
        });
    }

}