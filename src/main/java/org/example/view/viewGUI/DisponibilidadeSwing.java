package org.example.view.viewGUI;

import org.example.model.DisponibilidadeEntity;
import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.List;
import org.example.model.repositories.DisponibilidadeRepository;
import javax.swing.table.DefaultTableModel;

public class DisponibilidadeSwing extends JFrame {

    private DisponibilidadeRepository disponibilidadeRepo;
    public DisponibilidadeSwing(EntityManager em) {
        this.disponibilidadeRepo = new DisponibilidadeRepository(em);

        setTitle("Gerenciar Disponibilidade");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JLabel labelDia = new JLabel("Selecione o dia da semana:");
        String[] diasSemana = {
                "Domingo", "Segunda-feira", "Terça-feira",
                "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"
        };
        JComboBox<String> comboDia = new JComboBox<>(diasSemana);
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

        //Tabela com as informaçoes da disponibilidade dos dias da semana
        String[] columnNames = {"ID", "Hora Início", "Hora Fim", "Dia Todo", "Bloqueado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        JTextField fieldHoraInicio = new JTextField();
        JTextField fieldHoraFim = new JTextField();
        JCheckBox checkDiaTodo = new JCheckBox("Dia Todo");
        JCheckBox checkBloqueado = new JCheckBox("Bloqueado");
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

        add(formPanel, BorderLayout.SOUTH);

        //Mostrar a situaçao do evento, seja um erro ou nao
        JLabel labelStatus = new JLabel(" ");
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatus.setForeground(Color.RED);
        add(labelStatus, BorderLayout.PAGE_END);

        //Adicionando o evento de buscar a disponibilidade do dia escolhido
        btnBuscar.addActionListener(e -> {
            String diaSemana = comboDia.getSelectedItem().toString().toLowerCase();

            tableModel.setRowCount(0); // Limpar tabela

            List<DisponibilidadeEntity> disponiveis = disponibilidadeRepo.diaDisponivel(diaSemana);

            if (disponiveis == null || disponiveis.isEmpty()) {
                labelStatus.setText("Nenhuma disponibilidade encontrada para " + diaSemana);
            } else {
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
        });

        //Selecionar a linha da tabela
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                fieldHoraInicio.setText(tableModel.getValueAt(selectedRow, 1).toString());
                fieldHoraFim.setText(tableModel.getValueAt(selectedRow, 2).toString());
                checkDiaTodo.setSelected((boolean) tableModel.getValueAt(selectedRow, 3));
                checkBloqueado.setSelected((boolean) tableModel.getValueAt(selectedRow, 4));
            }
        });

        //Atualizar a disponibildiade
        btnAtualizar.addActionListener(e -> {
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

                disponibilidadeRepo.atualizarDisponibilidade(id, horaInicio, horaFim, isDiaTodo, isBloqueado);

                labelStatus.setForeground(Color.GREEN);
                labelStatus.setText("Disponibilidade atualizada com sucesso!");

                // Atualiza a tabela
                tableModel.setValueAt(horaInicio, selectedRow, 1);
                tableModel.setValueAt(horaFim, selectedRow, 2);
                tableModel.setValueAt(isDiaTodo, selectedRow, 3);
                tableModel.setValueAt(isBloqueado, selectedRow, 4);

            } catch (Exception ex) {
                labelStatus.setForeground(Color.RED);
                labelStatus.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });
    }

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            DisponibilidadeSwing frmMenuDisponibilidade = new DisponibilidadeSwing(em);
            frmMenuDisponibilidade.setVisible(true);
        });
    }

}

    /*
    public static String getDiaSemana(int diaSelect) {
        switch (diaSelect) {
            case 1: return "domingo";
            case 2: return "segunda-feira";
            case 3: return "terça-feira";
            case 4: return "quarta-feira";
            case 5: return "quinta-feira";
            case 6: return "sexta-feira";
            case 7: return "sábado";
            default: return null;
        }
    }
     */
