package org.example.view;

import org.example.control.repositories.AgendamentoRepository;
import org.example.model.AgendamentoEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisuAgendSwing extends JFrame {

    private final AgendamentoRepository agendamentoRepo = new AgendamentoRepository();

    public VisuAgendSwing() {
        setTitle("Gerenciamento de Agendamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Menu de Agendamentos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnVerTodos = new JButton("Ver todos os agendamentos");
        JButton btnAlterar = new JButton("Alterar agendamento");
        JButton btnProximo = new JButton("Ver próximo agendamento");
        JButton btnSair = new JButton("Sair");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new GridLayout(4, 1, 10, 10));
        botoesPanel.add(btnVerTodos);
        botoesPanel.add(btnAlterar);
        botoesPanel.add(btnProximo);
        botoesPanel.add(btnSair);

        add(titulo, BorderLayout.NORTH);
        add(botoesPanel, BorderLayout.CENTER);

        // Ações dos botões
        btnVerTodos.addActionListener(e -> mostrarTodosAgendamentos());
        btnProximo.addActionListener(e -> mostrarProximoAgendamento());
        btnAlterar.addActionListener(e -> alterarAgendamento());
        btnSair.addActionListener(e -> dispose());
    }

    // Método para abrir a tela
    public static void abrirTela() {
        SwingUtilities.invokeLater(() -> {
            VisuAgendSwing janela = new VisuAgendSwing();
            janela.setVisible(true);
        });
    }

    private void mostrarTodosAgendamentos() {
        List<AgendamentoEntity> agendamentos = agendamentoRepo.listarAgendamentos();

        if (agendamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum agendamento encontrado.");
            return;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Data/Hora");
        model.addColumn("Status");
        model.addColumn("Descrição");

        for (AgendamentoEntity ag : agendamentos) {
            model.addRow(new Object[]{
                    ag.getId(),
                    ag.getDataHora(),
                    ag.getStatus(),
                    ag.getDescricao()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Todos os Agendamentos");
        frame.setSize(600, 400);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostrarProximoAgendamento() {
        AgendamentoEntity proximo = agendamentoRepo.buscarProximoAgendamento();

        if (proximo == null) {
            JOptionPane.showMessageDialog(this, "Não há próximos agendamentos.");
            return;
        }

        String mensagem = String.format(
                "ID: %d\nData/Hora: %s\nStatus: %s\nDescrição: %s",
                proximo.getId(),
                proximo.getDataHora(),
                proximo.getStatus(),
                proximo.getDescricao()
        );

        JOptionPane.showMessageDialog(this, mensagem, "Próximo Agendamento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alterarAgendamento() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do agendamento:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            String dataStr = JOptionPane.showInputDialog(this, "Digite a nova data (dd/MM/yyyy):");
            if (dataStr == null) return;
            String horaStr = JOptionPane.showInputDialog(this, "Digite o novo horário (HH:mm):");
            if (horaStr == null) return;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(dataStr + " " + horaStr, formatter);

            String status = JOptionPane.showInputDialog(this, "Digite o novo status:");
            String descricao = JOptionPane.showInputDialog(this, "Digite a nova descrição:");

            agendamentoRepo.atualizarAgendamento(id, dataHora, status, descricao);

            JOptionPane.showMessageDialog(this, "Agendamento atualizado com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }
}
