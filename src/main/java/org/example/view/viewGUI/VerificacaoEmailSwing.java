package org.example.view.viewGUI;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import org.example.model.entities.UsuariosEntity;
import org.example.control.services.UsuarioService;

public class VerificacaoEmailSwing extends JFrame {

    private final UsuariosEntity usuario;
    private final EntityManager em;
    private UsuarioService usuarioService;

    private JLabel lblTitulo, lblInstrucao, lblEmailInfo, lblTentativas;
    private JTextField txtToken;
    private JButton btnVoltar, btnVerificar;
    private JPanel panel;

    private int tentativasRestantes = 3;

    public VerificacaoEmailSwing(EntityManager em, UsuariosEntity usuario) {
        super("Confirmação de E-mail");
        this.usuario = usuario;
        this.em = em;
        this.usuarioService = new UsuarioService(em);

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        usuarioService.atualizarTokenConfirmacao(usuario);
        usuarioService.enviarTokenEmail(usuario.getEmail());
        adicionarListeners();
        setVisible(true);
    }

    private void configurarJanela() {
        setSize(450, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void inicializarComponentes() {
        lblTitulo = new JLabel("Confirmação de E-mail", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(95, 10, 240, 40);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(60, 60, 310, 170);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        lblInstrucao = new JLabel("<html>Digite o TOKEN enviado para seu e-mail<br>para confirmarmos sua autenticidade.</html>");
        lblInstrucao.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblInstrucao.setBounds(10, 10, 290, 40);

        txtToken = new JTextField();
        txtToken.setFont(new Font("Tahoma", Font.BOLD, 20));
        txtToken.setHorizontalAlignment(JTextField.CENTER);
        txtToken.setBounds(10, 60, 290, 40);

        lblEmailInfo = new JLabel("enviado para: " + usuario.getEmail());
        lblEmailInfo.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblEmailInfo.setForeground(Color.DARK_GRAY);
        lblEmailInfo.setBounds(10, 110, 195, 20);

        lblTentativas = new JLabel("");
        lblTentativas.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblTentativas.setForeground(Color.RED);
        lblTentativas.setBounds(10, 135, 200, 20);

        btnVerificar = new JButton("Verificar");
        btnVerificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVerificar.setBounds(215, 110, 85, 21);

        btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnVoltar.setBounds(0, 0, 57, 21);
    }

    private void montarLayout() {
        add(lblTitulo);
        add(panel);
        add(btnVoltar);

        panel.add(lblInstrucao);
        panel.add(txtToken);
        panel.add(lblEmailInfo);
        panel.add(btnVerificar);
        panel.add(lblTentativas);
    }

    private void adicionarListeners() {
        btnVerificar.addActionListener((ActionEvent e) -> {
            try {
                String tokenDigitado = txtToken.getText().trim();

                JOptionPane.showMessageDialog(this, "Código de verificação enviado.");

                if (tokenDigitado.isEmpty()) {
                    throw new IllegalArgumentException("O campo de token está vazio.");
                }

                if (tokenDigitado.equals(usuario.getConfirmcode())) {
                    usuarioService.editarIsActive(usuario);
                    usuario.setConfirmcode(null);

                    dispose();
                    AgendamentoSwing.mostrar(em, usuario);
                } else {
                    tentativasRestantes--;
                    lblTentativas.setText("Tentativas restantes: " + tentativasRestantes);

                    if (tentativasRestantes <= 0) {
                        JOptionPane.showMessageDialog(this, "Número máximo de tentativas atingido.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Token incorreto. Tente novamente.");
                    }
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnVoltar.addActionListener(e -> dispose());
    }

    public static void mostrar(EntityManager em, UsuariosEntity usuario) {
        SwingUtilities.invokeLater(() -> new VerificacaoEmailSwing(em, usuario));
    }
}
