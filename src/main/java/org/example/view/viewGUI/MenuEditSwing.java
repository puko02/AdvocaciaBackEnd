package org.example.view.viewGUI;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

import org.example.control.services.UsuarioService;
import org.example.model.entities.UsuariosEntity;
import org.example.view.viewGUI.MenuAdmin;

public class MenuEditSwing extends JFrame {

    // Componentes principais
    private JButton btnEditarNome, btnEditarTelefone, btnEditarSenha, btnExcluir, btnVoltar;
    private JRadioButton rbtnAdmin, rbtnUsuarioComum, rbtnAtivo, rbtnInativo;
    private JLabel lblTitulo, lblEmailInfo, lblNome, lblTelefone, lblSenha;
    private JPanel panelDados, panelBotoes, panel;
    private EntityManager em;
    private UsuariosEntity usuario;
    private UsuarioService usuarioService;

    public MenuEditSwing(EntityManager em, UsuariosEntity usuario) {
        super("Menu de Edição de Usuário");
        this.usuario = usuario;
        this.em = em;
        this.usuarioService = new UsuarioService(em);
        configurarJanela();
        inicializarComponentes();
        montarLayout();
        adicionarListeners();
        setVisible(true);
    }

    private void configurarJanela() {
        setSize(400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void inicializarComponentes() {
        // Labels
        lblTitulo = new JLabel("Bem vindo ao menu de edição de usuários!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(20, 20, 348, 30);

        lblEmailInfo = new JLabel("Dados atuais do usuário de email: teste@email.com", SwingConstants.CENTER);
        lblEmailInfo.setBounds(44, 55, 296, 22);

        lblNome = new JLabel("Nome: " + usuario.getNome());
        lblTelefone = new JLabel("Telefone: " + usuario.getTelefone());
        lblSenha = new JLabel("Senha: " + usuario.getSenha());

        // Painel dados
        panelDados = new JPanel();
        panelDados.setLayout(new BoxLayout(panelDados, BoxLayout.Y_AXIS));
        panelDados.setBounds(70, 90, 245, 60);
        panelDados.add(lblNome);
        panelDados.add(lblTelefone);
        panelDados.add(lblSenha);

        // Painel de opções
        panel = new JPanel();
        panel.setBounds(70, 165, 245, 269);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        btnEditarNome = new JButton("Editar nome");
        btnEditarTelefone = new JButton("Editar telefone");
        btnEditarSenha = new JButton("Editar senha");

        configurarBotao(btnEditarNome);
        configurarBotao(btnEditarTelefone);
        configurarBotao(btnEditarSenha);

        // Radio buttons
        JLabel lblPermissao = new JLabel("Alterar as permissões para:");
        lblPermissao.setFont(new Font("Tahoma", Font.PLAIN, 15));

        rbtnAdmin = new JRadioButton("Admin");
        rbtnUsuarioComum = new JRadioButton("Usuário comum");

        ButtonGroup grupoPermissao = new ButtonGroup();
        grupoPermissao.add(rbtnAdmin);
        grupoPermissao.add(rbtnUsuarioComum);

        JLabel lblStatus = new JLabel("A conta desse usuário está:");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));

        rbtnAtivo = new JRadioButton("Ativo");
        rbtnInativo = new JRadioButton("Inativo");

        ButtonGroup grupoStatus = new ButtonGroup();
        grupoStatus.add(rbtnAtivo);
        grupoStatus.add(rbtnInativo);

        // Botão de exclusão
        btnExcluir = new JButton("Excluir usuário");
        btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnExcluir.setBackground(Color.RED);
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBounds(70, 460, 245, 40);

        // Botão de voltar
        btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnVoltar.setBounds(0, 0, 57, 21);

        // Adicionando componentes ao painel
        panel.add(btnEditarNome);
        panel.add(btnEditarTelefone);
        panel.add(btnEditarSenha);
        panel.add(lblPermissao);
        panel.add(rbtnAdmin);
        panel.add(rbtnUsuarioComum);
        if (usuario.isAdmin()) {
            rbtnAdmin.setSelected(true);
        } else {
            rbtnUsuarioComum.setSelected(true);
        }

        panel.add(lblStatus);
        panel.add(rbtnAtivo);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(rbtnInativo);
        if (usuario.isActive()) {
            rbtnAtivo.setSelected(true);
        } else {
            rbtnInativo.setSelected(true);
        }
    }

    private void montarLayout() {
        add(lblTitulo);
        add(lblEmailInfo);
        add(panelDados);
        add(panel);
        add(btnExcluir);
        add(btnVoltar);
    }

    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("Tahoma", Font.PLAIN, 16));
    }

    private void adicionarListeners() {
        btnEditarNome.addActionListener(e -> System.out.println("Editar nome clicado"));
        btnEditarTelefone.addActionListener(e -> System.out.println("Editar telefone clicado"));
        btnEditarSenha.addActionListener(e -> System.out.println("Editar senha clicado"));
        btnExcluir.addActionListener(e -> System.out.println("Excluir clicado"));
        rbtnAdmin.addItemListener(e -> {
            if (rbtnAdmin.isSelected() && !usuario.isAdmin()) {
                JOptionPane.showMessageDialog(this, "Permissão alterada para ADMIN!");
                usuarioService.editarIsAdmin(usuario);
            }
        });

        rbtnUsuarioComum.addItemListener(e -> {
            if (rbtnUsuarioComum.isSelected() && usuario.isAdmin()) {
                JOptionPane.showMessageDialog(this, "Permissão alterada para USUÁRIO COMUM!");
                usuarioService.editarIsAdmin(usuario);
            }
        });

        rbtnAtivo.addItemListener(e -> {
            if (rbtnAtivo.isSelected() && !usuario.isActive()) {
                JOptionPane.showMessageDialog(this, "Status alterado para ATIVO!");
                usuarioService.editarIsActive(usuario);
            }
        });

        rbtnInativo.addItemListener(e -> {
            if (rbtnInativo.isSelected() && usuario.isActive()) {
                JOptionPane.showMessageDialog(this, "Status alterado para INATIVO!");
                usuarioService.editarIsActive(usuario);
            }
        });
        btnVoltar.addActionListener(e -> {
            dispose();
            System.out.println("Voltar clidado");
        });
    }

    public static void mostrar(EntityManager em, UsuariosEntity usuario) {
        SwingUtilities.invokeLater(() -> new MenuEditSwing(em, usuario));
    }
}