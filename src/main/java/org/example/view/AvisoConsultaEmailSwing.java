package org.example.view;

import org.example.control.services.UsuarioService;
import org.example.model.entities.UsuariosEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class AvisoConsultaEmailSwing extends JDialog {
    private final EntityManager em;
    private UsuarioService usuarioService;

    private JLabel lblIcone, lblTexto;
    private JTextField txtEmail;
    private JButton btnEntrar;

    private UsuariosEntity usuarioRetorno;

    public AvisoConsultaEmailSwing(EntityManager em, JFrame parent) {
        super(parent, "Login de Email para consulta", true); // <- JDialog modal
        this.em = em;
        this.usuarioService = new UsuarioService(em);
        configurarJanela();
        inicializarComponentes();
        montarLayout();
        adicionarListeners();
    }

    private void configurarJanela() {
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void inicializarComponentes() {
        lblIcone = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
        lblIcone.setBounds(30, 50, 50, 50);

        lblTexto = new JLabel("Digite um e-mail para poder vincular a sua consulta", SwingConstants.CENTER);
        lblTexto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTexto.setBounds(90, 20, 370, 25);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtEmail.setHorizontalAlignment(JTextField.CENTER);
        txtEmail.setBounds(90, 60, 300, 30);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEntrar.setBounds(400, 60, 75, 30);
    }

    private void montarLayout() {
        add(lblIcone);
        add(lblTexto);
        add(txtEmail);
        add(btnEntrar);
    }

    private void adicionarListeners() {
        btnEntrar.addActionListener(e -> {
            try {
                String email = txtEmail.getText().trim();
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, digite um e-mail.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.contains("@")){
                    JOptionPane.showMessageDialog(this, "Digite um e-mail valido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Optional<UsuariosEntity> existente = usuarioService.buscarPorEmail(email);

                if (existente.isEmpty()) {
                    UsuariosEntity usuario = new UsuariosEntity();
                    try {
                        em.getTransaction().begin();
                        usuarioService.salvarUsuario(usuario, "", "", email);
                        em.getTransaction().commit();
                    } catch (Exception ex) {
                        em.getTransaction().rollback();
                        JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    usuarioRetorno = usuario;
                    JOptionPane.showMessageDialog(this, "E-mail não encontrado. Novo usuário criado.", "Usuário não encontrado", JOptionPane.WARNING_MESSAGE);
                    dispose();
                } else {
                    usuarioRetorno = existente.get();
                    dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar e-mail: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    public static UsuariosEntity solicitarEmail(EntityManager em) {
        AvisoConsultaEmailSwing dialog = new AvisoConsultaEmailSwing(em, null);
        dialog.setVisible(true);
        return dialog.usuarioRetorno;
    }
}
