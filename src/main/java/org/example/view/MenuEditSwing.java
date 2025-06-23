package org.example.view;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import org.example.control.services.UsuarioService;
import org.example.model.entities.UsuariosEntity;

public class MenuEditSwing extends JFrame {

    // Componentes principais
    private JButton btnEditarNome, btnEditarTelefone, btnEditarSenha, btnVisualizarSenha, btnExcluir, btnVoltar;
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

        lblEmailInfo = new JLabel("E-mail vinculado: " + usuario.getEmail(), SwingConstants.CENTER);
        lblEmailInfo.setBounds(44, 55, 296, 22);

        lblNome = new JLabel("Nome: " + usuario.getNome());
        lblTelefone = new JLabel("Telefone: " + usuario.getTelefone());
        lblSenha = new JLabel("Senha: *****"); // Não exiba a senha real, apenas um placeholder

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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        btnEditarNome = new JButton("Editar nome");
        btnEditarTelefone = new JButton("Editar telefone");
        btnEditarSenha = new JButton("Editar senha");

        // Novo botão de visualização de senha com emoji e tamanho reduzido
        btnVisualizarSenha = new JButton("👁️"); // Emoji de olho
        btnVisualizarSenha.setPreferredSize(new Dimension(40, 25)); // Tamanho pequeno
        btnVisualizarSenha.setMargin(new Insets(0, 0, 0, 0)); // Remover margens internas para o emoji caber melhor
        btnVisualizarSenha.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16)); // Fonte para garantir que o emoji seja exibido

        configurarBotao(btnEditarNome);
        configurarBotao(btnEditarTelefone);
        configurarBotao(btnEditarSenha);
        // Não chame configurarBotao para btnVisualizarSenha, pois ele tem configurações específicas de tamanho e fonte

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

        // Criar um sub-painel para os botões de senha para melhor organização
        JPanel passwordButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // FlowLayout para os botões de senha
        passwordButtonsPanel.add(btnEditarSenha);
        passwordButtonsPanel.add(btnVisualizarSenha); // Adicionar o botão de visualização aqui
        panel.add(passwordButtonsPanel); // Adicionar o sub-painel ao painel principal

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
        btnEditarNome.addActionListener(e -> handleEditName());
        btnEditarTelefone.addActionListener(e -> handleEditTelefone());
        btnEditarSenha.addActionListener(e -> handleEditSenha());
        btnVisualizarSenha.addActionListener(e -> handleViewSenha()); // Listener para o novo botão
        btnExcluir.addActionListener(e -> handleDeleteUser());
        rbtnAdmin.addItemListener(e -> {
            if (rbtnAdmin.isSelected() && !usuario.isAdmin()) {
                try {
                    usuarioService.editarIsAdmin(usuario);
                    JOptionPane.showMessageDialog(this, "Permissão alterada para ADMIN!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar permissão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rbtnUsuarioComum.addItemListener(e -> {
            if (rbtnUsuarioComum.isSelected() && usuario.isAdmin()) {
                try {
                    usuarioService.editarIsAdmin(usuario);
                    JOptionPane.showMessageDialog(this, "Permissão alterada para USUÁRIO COMUM!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar permissão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rbtnAtivo.addItemListener(e -> {
            if (rbtnAtivo.isSelected() && !usuario.isActive()) {
                try {
                    usuarioService.editarIsActive(usuario);
                    JOptionPane.showMessageDialog(this, "Status alterado para ATIVO!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rbtnInativo.addItemListener(e -> {
            if (rbtnInativo.isSelected() && usuario.isActive()) {
                try {
                    usuarioService.editarIsActive(usuario);
                    JOptionPane.showMessageDialog(this, "Status alterado para INATIVO!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnVoltar.addActionListener(e -> {
            dispose();
            System.out.println("Voltar clicado");
        });
    }

    // --- Métodos para lidar com os popups de edição ---

    private void handleEditName() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel promptLabel = new JLabel("Digite o novo nome:");
        JLabel oldNameLabel = new JLabel("Nome anterior: " + usuario.getNome());
        JTextField newNameField = new JTextField(20);

        panel.add(promptLabel);
        panel.add(oldNameLabel);
        panel.add(newNameField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Nome",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String novoNome = newNameField.getText();
            try {
                usuarioService.editarNome(usuario, novoNome);
                lblNome.setText("Nome: " + usuario.getNome()); // Atualiza o label na tela principal
                JOptionPane.showMessageDialog(this, "Nome alterado com sucesso para: " + usuario.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Editar Nome", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao editar o nome: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void handleEditTelefone() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel promptLabel = new JLabel("Digite o novo telefone:");
        JLabel oldPhoneLabel = new JLabel("Telefone anterior: " + usuario.getTelefone());
        JTextField newPhoneField = new JTextField(20);

        panel.add(promptLabel);
        panel.add(oldPhoneLabel);
        panel.add(newPhoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Telefone",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String novoTelefone = newPhoneField.getText();
            try {
                usuarioService.editarTelefone(usuario, novoTelefone);
                lblTelefone.setText("Telefone: " + usuario.getTelefone()); // Atualiza o label na tela principal
                JOptionPane.showMessageDialog(this, "Telefone alterado com sucesso para: " + usuario.getTelefone(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Editar Telefone", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao editar o telefone: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void handleEditSenha() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel promptLabel = new JLabel("Digite sua nova senha:");
        JLabel oldPasswordLabel = new JLabel("Senha anterior: *****");
        JPasswordField newPasswordField = new JPasswordField(20);

        panel.add(promptLabel);
        panel.add(oldPasswordLabel);
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Senha",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String novaSenha = new String(newPasswordField.getPassword());
            try {
                usuarioService.editarSenha(usuario, novaSenha);
                lblSenha.setText("Senha: *****");
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Editar Senha", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao editar a senha: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void handleViewSenha() {
        String senha = usuario.getSenha();
        JOptionPane.showMessageDialog(this, "Senha : \n" + senha, "Visualizar Senha", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleDeleteUser(){
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este usuário?\nEsta ação é irreversível.",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (usuario.isAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Não é permitido excluir um usuário com status de ADMIN.",
                    "Ação proibida",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuarioService.excluirUsuario(usuario);
            JOptionPane.showMessageDialog(this,
                    "Usuário excluído com sucesso.",
                    "Exclusão concluída",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir usuário: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void mostrar(EntityManager em, UsuariosEntity usuario) {
        SwingUtilities.invokeLater(() -> new MenuEditSwing(em, usuario));
    }
}
