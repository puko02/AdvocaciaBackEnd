package org.example.view.viewGUI;

import org.example.control.services.UsuarioService;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class CadastroSwing extends JFrame {
    private EntityManager em;
    private UsuariosEntity usuario;
    private UsuarioService usuarioService;

    private JLabel lblTitulo, lblEmailNaoEncontrado, lblAviso;
    private JTextField nomeField, telefoneField;
    private JButton btnEnviar;

    public CadastroSwing(EntityManager em, UsuariosEntity usuario) {
        super("Cadastro");
        this.em = em;
        this.usuario = usuario;
        this.usuarioService = new UsuarioService(em);


        configurarJanela();
        inicializarComponentes();
        montarLayout();
        adicionarListeners();
        setVisible(true);
        System.out.println(usuario + "\nUsuario excluido com sucesso");
        usuarioService.excluirUsuario(usuario);
    }

    private void configurarJanela() {
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void inicializarComponentes() {
        lblEmailNaoEncontrado = new JLabel("Email " + usuario.getEmail() + " não encontrado", SwingConstants.CENTER);
        lblEmailNaoEncontrado.setFont(new Font("Berlin Sans FB", Font.ITALIC, 16));
        lblEmailNaoEncontrado.setBounds(30, 10, 340, 25);

        lblTitulo = new JLabel("Inicializando cadastro", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setBounds(30, 35, 340, 25);

        lblAviso = new JLabel("Por favor, preencha os dados abaixo:", SwingConstants.CENTER);
        lblAviso.setFont(new Font("Arial", Font.BOLD, 12));
        lblAviso.setBounds(30, 60, 340, 25);

        nomeField = new JTextField();
        nomeField.setBounds(130, 100, 200, 25);

        telefoneField = new JTextField();
        telefoneField.setBounds(130, 135, 200, 25);

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBounds(250, 170, 80, 30);
    }

    private void montarLayout() {
        add(lblEmailNaoEncontrado);
        add(lblTitulo);
        add(lblAviso);

        add(new JLabel("Nome:")).setBounds(50, 100, 80, 25);
        add(nomeField);

        add(new JLabel("Telefone:")).setBounds(50, 135, 80, 25);
        add(telefoneField);

        add(btnEnviar);
    }

    private void adicionarListeners() {
        btnEnviar.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String telefone = telefoneField.getText().trim();

            if (nome.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuario.setNome(nome);
            usuario.setTelefone(telefone);
            try {
                em.getTransaction().begin();
                usuarioService.salvarUsuario(usuario, nome, telefone, usuario.getEmail());
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex){
                em.getTransaction().rollback();
                JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });


        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Se o usuário não preencher os campos, o usuário será excluído
                if ((nomeField.getText().trim().isEmpty() || telefoneField.getText().trim().isEmpty()) && usuario.getId() != null) {
                    usuarioService.excluirUsuario(usuario);
                    System.out.println("Usuário excluído porque não finalizou o cadastro.");
                }
            }
        });
    }

    public static void mostrar(EntityManager em, UsuariosEntity usuario) {
        SwingUtilities.invokeLater(() -> new CadastroSwing(em, usuario));
    }
}
