package org.example.view;

import org.example.control.services.DisponibilidadeController;
import org.example.model.entities.DisponibilidadeEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.List;

public class DisponibilidadeSwing extends JFrame {

    private final DisponibilidadeController controller;

    private JComboBox<String> comboDia;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField fieldHoraInicio;
    private JTextField fieldHoraFim;
    private JCheckBox checkDiaTodo;
    private JCheckBox checkBloqueado;
    private JLabel labelStatus;

    public DisponibilidadeSwing(DisponibilidadeController controller) {
        this.controller = controller;

        setTitle("Gerenciar Disponibilidade");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        montarComponentes();
        adicionarEventos();
    }

    private void montarComponentes() {
        JPanel topPanel = new JPanel(new FlowLayout());

        JLabel labelDia = new JLabel("Selecione o dia da semana:");
        String[] diasSemana = {
                "Domingo", "Segunda-feira", "Terça-feira",
                "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"
        };
        comboDia = new JComboBox<>(diasSemana);
        JButton btnBuscar = new JButton("Buscar");

        JButton btnRetornar = new JButton("Retornar");
        btnRetornar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        topPanel.add(labelDia);
        topPanel.add(comboDia);
        topPanel.add(btnBuscar);
        topPanel.add(btnRetornar);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Hora Início", "Hora Fim", "Dia Todo", "Bloqueado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        fieldHoraInicio = new JTextField();
        fieldHoraFim = new JTextField();
        checkDiaTodo = new JCheckBox("Dia Todo");
        checkBloqueado = new JCheckBox("Bloqueado");
        JButton btnAtualizar = new JButton("Atualizar Disponibilidade");

        formPanel.add(new JLabel("Hora Início (HH:MM:SS):"));
        formPanel.add(fieldHoraInicio);
        formPanel.add(new JLabel("Hora Fim (HH:MM:SS):"));
        formPanel.add(fieldHoraFim);
        formPanel.add(checkDiaTodo);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(checkBloqueado);
        formPanel.add(new JLabel());
        formPanel.add(btnAtualizar);

        labelStatus = new JLabel(" ");
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatus.setForeground(Color.RED);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(labelStatus, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarDisponibilidade());
        btnAtualizar.addActionListener(e -> atualizarDisponibilidade());
    }

    private void adicionarEventos() {
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                fieldHoraInicio.setText(tableModel.getValueAt(selectedRow, 1).toString());
                fieldHoraFim.setText(tableModel.getValueAt(selectedRow, 2).toString());
                checkDiaTodo.setSelected((boolean) tableModel.getValueAt(selectedRow, 3));
                checkBloqueado.setSelected((boolean) tableModel.getValueAt(selectedRow, 4));
            }
        });
    }

    private void buscarDisponibilidade() {
        String diaSemana = comboDia.getSelectedItem().toString().toLowerCase();
        List<DisponibilidadeEntity> disponiveis = controller.buscarPorDia(diaSemana);

        tableModel.setRowCount(0);

        if (disponiveis.isEmpty()) {
            labelStatus.setText("Nenhuma disponibilidade encontrada para " + diaSemana);
        } else {
            labelStatus.setForeground(Color.GREEN);
            labelStatus.setText("Disponibilidades carregadas para " + diaSemana);
            for (DisponibilidadeEntity disp : disponiveis) {
                Object[] row = {
                        disp.getId(),
                        disp.getHoraInicio(),
                        disp.getHoraFim(),
                        disp.isDiaTodo(),
                        disp.isBloqueado()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void atualizarDisponibilidade() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            labelStatus.setText("Selecione uma disponibilidade na tabela.");
            return;
        }

        try {
            Long id = Long.valueOf(tableModel.getValueAt(selectedRow, 0).toString());
            Time horaInicio = Time.valueOf(fieldHoraInicio.getText());
            Time horaFim = Time.valueOf(fieldHoraFim.getText());
            boolean isDiaTodo = checkDiaTodo.isSelected();
            boolean isBloqueado = checkBloqueado.isSelected();

            boolean atualizado = controller.atualizarDisponibilidade(id, horaInicio, horaFim, isDiaTodo, isBloqueado);

            if (atualizado) {
                labelStatus.setForeground(Color.GREEN);
                labelStatus.setText("Disponibilidade atualizada com sucesso!");

                tableModel.setValueAt(horaInicio, selectedRow, 1);
                tableModel.setValueAt(horaFim, selectedRow, 2);
                tableModel.setValueAt(isDiaTodo, selectedRow, 3);
                tableModel.setValueAt(isBloqueado, selectedRow, 4);
            } else {
                labelStatus.setForeground(Color.RED);
                labelStatus.setText("Erro ao atualizar disponibilidade.");
            }
        } catch (Exception ex) {
            labelStatus.setForeground(Color.RED);
            labelStatus.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    public static void abrirTela(DisponibilidadeController controller) {
        SwingUtilities.invokeLater(() -> {
            DisponibilidadeSwing frm = new DisponibilidadeSwing(controller);
            frm.setVisible(true);
        });
    }
}
