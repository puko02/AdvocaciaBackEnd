package org.example.view;

import org.example.control.repositories.AnotacaoRepository;
import org.example.control.repositories.IAnotacaoRepository;
import org.example.control.services.UsuarioService;
import org.example.model.AnotacaoEntity;
import org.example.model.UsuariosEntity;

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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Menu de Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnListar = new JButton("Listar Clientes");
        JButton btnAdicionarNota = new JButton("Adicionar Nota");
        JButton btnVerNotas = new JButton("Ver Notas de um Cliente");
        JButton btnSair = new JButton("Sair");

        JPanel botoes = new JPanel();
        botoes.setLayout(new GridLayout(4, 1, 10, 10));
        botoes.add(btnListar);
        botoes.add(btnAdicionarNota);
        botoes.add(btnVerNotas);
        botoes.add(btnSair);

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);

        // Ações dos botões
        btnListar.addActionListener(e -> listarClientes());
        btnAdicionarNota.addActionListener(e -> adicionarNota());
        btnVerNotas.addActionListener(e -> verNotas());
        btnSair.addActionListener(e -> dispose());
    }

    // Método para abrir a janela
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

        JFrame frame = new JFrame("Lista de Clientes");
        frame.setSize(600, 400);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void adicionarNota() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do cliente:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            Optional<UsuariosEntity> clienteOpt = usuarioService.buscarPorId(id);
            if (clienteOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                return;
            }

            String conteudo = JOptionPane.showInputDialog(this, "Digite o conteúdo da nota:");
            if (conteudo == null || conteudo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nota vazia. Operação cancelada.");
                return;
            }

            anotacaoRepo.adicionarAnotacao(id, conteudo);
            JOptionPane.showMessageDialog(this, "Nota adicionada com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void verNotas() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do cliente:");
            if (idStr == null) return;
            Long id = Long.parseLong(idStr);

            List<AnotacaoEntity> notas = anotacaoRepo.buscarAnotacoesPorCliente(id);

            if (notas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma nota encontrada.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (AnotacaoEntity nota : notas) {
                sb.append("ID da Nota: ").append(nota.getId()).append("\n");
                sb.append("Conteúdo: ").append(nota.getNota()).append("\n");
                sb.append("Data de Criação: ").append(nota.getDataCriacao()).append("\n\n");
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(this, scroll, "Notas do Cliente", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }
}
