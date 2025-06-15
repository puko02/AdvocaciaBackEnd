package org.example.view.viewGUI;

import org.example.model.repositories.AgendamentoRepository;
import org.example.model.entities.AgendamentoEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisuAgendSwing extends JFrame {

    private final AgendamentoRepository agendamentoRepo = new AgendamentoRepository();

    public VisuAgendSwing() {
        setTitle("Menu de Agendamentos");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("📅 Gerenciador de Agendamentos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JButton btnVerTodos = new JButton("📋 Ver todos");
        JButton btnAlterar = new JButton("✏️ Alterar agendamento");
        JButton btnProximo = new JButton("⏭️ Próximo agendamento");
        JButton btnSair = new JButton("❌ Sair");

        JPanel botoesPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        botoesPanel.add(btnVerTodos);
        botoesPanel.add(btnAlterar);
        botoesPanel.add(btnProximo);
        botoesPanel.add(btnSair);

        add(titulo, BorderLayout.NORTH);
        add(botoesPanel, BorderLayout.CENTER);

        btnVerTodos.addActionListener(e -> mostrarTodosAgendamentos());
        btnProximo.addActionListener(e -> mostrarProximoAgendamento());
        btnAlterar.addActionListener(e -> alterarAgendamento());
        btnSair.addActionListener(e -> dispose());
    }

    public static void abrirTela() {
        SwingUtilities.invokeLater(() -> new VisuAgendSwing().setVisible(true));
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

        JFrame frame = new JFrame("📋 Lista de Agendamentos");
        frame.setSize(600, 300);
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

        JOptionPane.showMessageDialog(this, mensagem, "⏭️ Próximo Agendamento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alterarAgendamento() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            String dataStr = JOptionPane.showInputDialog(this, "Data (dd/MM/yyyy):");
            if (dataStr == null) return;
            String horaStr = JOptionPane.showInputDialog(this, "Hora (HH:mm):");
            if (horaStr == null) return;

            LocalDateTime dataHora = LocalDateTime.parse(
                    dataStr + " " + horaStr,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            );

            String status = JOptionPane.showInputDialog(this, "Novo status:");
            String descricao = JOptionPane.showInputDialog(this, "Nova descrição:");

            agendamentoRepo.atualizarAgendamento(id, dataHora, status, descricao);

            JOptionPane.showMessageDialog(this, "✅ Agendamento atualizado!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
