package org.example.view.viewGUI;

import org.example.model.AgendamentoEntity;
import org.example.model.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuConsultasSwing extends JFrame {


    public MenuConsultasSwing(EntityManager em) {


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
        btnRetornar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

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

            if (email.isEmpty()) {
                labelStatus.setText("Por favor, digite um e-mail.");
                return;
            }

            // Limpar tabela anterior
            tableModel.setRowCount(0);

            try {
                String jpqlUsuario = "SELECT u FROM UsuariosEntity u WHERE u.email = :email";
                TypedQuery<UsuariosEntity> queryUsuario = em.createQuery(jpqlUsuario, UsuariosEntity.class);
                queryUsuario.setParameter("email", email);

                UsuariosEntity usuario = queryUsuario.getSingleResult();

                String jpqlAgendamento = "SELECT a FROM AgendamentoEntity a WHERE a.cliente = :usuario ORDER BY a.dataHora DESC";
                TypedQuery<AgendamentoEntity> queryAgendamentos = em.createQuery(jpqlAgendamento, AgendamentoEntity.class);
                queryAgendamentos.setParameter("usuario", usuario);

                List<AgendamentoEntity> agendamentos = queryAgendamentos.getResultList();

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

    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            MenuConsultasSwing frmMenuCon = new MenuConsultasSwing(em);
            frmMenuCon.setVisible(true);
        });
    }

}
