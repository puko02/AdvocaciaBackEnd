package org.example.view.viewGUI;

import org.example.control.services.MenuConsultasController;
import org.example.model.entities.AgendamentoEntity;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuConsultasSwing extends JFrame {

    private MenuConsultasController controller;

    public MenuConsultasSwing(EntityManager em) {
        this.controller = new MenuConsultasController(em);

        setTitle("Menu de Consultas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JLabel labelEmail = new JLabel("Digite o e-mail:");
        JTextField fieldEmail = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");

        JButton btnRetornar = new JButton("Retornar");
        btnRetornar.addActionListener(e -> dispose());

        topPanel.add(labelEmail);
        topPanel.add(fieldEmail);
        topPanel.add(btnBuscar);
        topPanel.add(btnRetornar);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Data/Hora", "Status", "Descrição"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JLabel labelStatus = new JLabel(" ");
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatus.setForeground(Color.RED);
        add(labelStatus, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            String email = fieldEmail.getText().trim();
            labelStatus.setText("");

            if (email.isEmpty()) {
                labelStatus.setText("Por favor, digite um e-mail.");
                return;
            }

            tableModel.setRowCount(0); // Clear table

            try {
                UsuariosEntity usuario = controller.buscarUsuarioPorEmail(email);
                List<AgendamentoEntity> agendamentos = controller.buscarAgendamentosPorUsuario(usuario);

                if (agendamentos.isEmpty()) {
                    labelStatus.setText("Nenhum agendamento encontrado para o e-mail: " + email);
                } else {
                    labelStatus.setText("Agendamentos encontrados: " + agendamentos.size());
                    for (AgendamentoEntity agendamento : agendamentos) {
                        Object[] row = {
                                agendamento.getId(),
                                agendamento.getDataHora(),
                                agendamento.getStatus(),
                                agendamento.getDescricao()
                        };
                        tableModel.addRow(row);
                    }
                }
            } catch (Exception ex) {
                labelStatus.setText("E-mail não encontrado.");
            }
        });
    }

    public static void abrirTela(MenuConsultasController controller) {
        SwingUtilities.invokeLater(() -> {
            MenuConsultasSwing frmMenuCon = new MenuConsultasSwing(controller);
            frmMenuCon.setVisible(true);
        });
    }
}
