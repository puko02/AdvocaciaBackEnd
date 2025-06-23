package org.example.view;

import org.example.model.repositories.AnotacaoRepository;
import org.example.model.repositories.IAnotacaoRepository;
import org.example.control.services.UsuarioService;
import org.example.model.entities.AnotacaoEntity;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class UsuariosSwing extends JFrame {

    private final UsuarioService usuarioService;
    private final IAnotacaoRepository anotacaoRepo;

    public UsuariosSwing(EntityManager em) {
        this.usuarioService = new UsuarioService(em);
        this.anotacaoRepo = new AnotacaoRepository(em);

        setTitle("Gerenciamento de Clientes e Notas");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("👤 Menu de Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        JButton btnListar = new JButton("📋 Listar Clientes");
        JButton btnAdicionarNota = new JButton("📝 Adicionar Nota");
        JButton btnVerNotas = new JButton("🔍 Ver Notas");
        JButton btnSair = new JButton("❌ Sair");

        JPanel botoes = new JPanel(new GridLayout(4, 1, 10, 10));
        botoes.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        botoes.add(btnListar);
        botoes.add(btnAdicionarNota);
        botoes.add(btnVerNotas);
        botoes.add(btnSair);

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);

        btnListar.addActionListener(e -> listarClientes());
        btnAdicionarNota.addActionListener(e -> adicionarNota());
        btnVerNotas.addActionListener(e -> verNotas());
        btnSair.addActionListener(e -> dispose());
    }

    // Método público para abrir a tela
    public static void abrirTela(EntityManager em) {
        SwingUtilities.invokeLater(() -> {
            UsuariosSwing janela = new UsuariosSwing(em);
            janela.setVisible(true);
        });
    }

    private void listarClientes() {
        List<UsuariosEntity> clientes = usuarioService.listarClientes();

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.");
            return;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("E-mail");
        model.addColumn("Telefone");

        for (UsuariosEntity cliente : clientes) {
            model.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getTelefone()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("📋 Lista de Clientes");
        frame.setSize(600, 300);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void adicionarNota() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID do cliente:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            Optional<UsuariosEntity> clienteOpt = usuarioService.buscarPorId(id);
            if (clienteOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                return;
            }

            String conteudo = JOptionPane.showInputDialog(this, "Conteúdo da nota:");
            if (conteudo == null || conteudo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nota vazia.");
                return;
            }

            anotacaoRepo.adicionarAnotacao(id, conteudo);
            JOptionPane.showMessageDialog(this, "Nota salva com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void verNotas() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID do cliente:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            List<AnotacaoEntity> notas = anotacaoRepo.buscarAnotacoesPorCliente(id);

            if (notas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma nota encontrada.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (AnotacaoEntity nota : notas) {
                sb.append("📝 Nota ID: ").append(nota.getId()).append("\n");
                sb.append("Conteúdo: ").append(nota.getNota()).append("\n");
                sb.append("Criado em: ").append(nota.getDataCriacao()).append("\n\n");
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(this, scroll, "🔍 Notas do Cliente", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }
}
