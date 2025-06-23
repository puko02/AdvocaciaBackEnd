package org.example.view.viewGUI;

import org.example.control.services.AgendamentoService;
import org.example.control.services.DisponibilidadeService;
import org.example.control.services.DateLabelFormatterService;
import org.example.model.entities.AgendamentoEntity;
import org.example.model.entities.DisponibilidadeEntity;
import org.example.model.entities.UsuariosEntity;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class AgendamentoSwing extends JFrame {

    private final EntityManager em;
    private final UsuariosEntity usuario;
    private final DisponibilidadeService disponibilidadeService;
    private final AgendamentoService agendamentoService;

    public AgendamentoSwing(EntityManager em, UsuariosEntity usuario) {
        super("Agendamento de Reunião");
        this.em = em;
        this.usuario = usuario;
        this.disponibilidadeService = new DisponibilidadeService(em);
        this.agendamentoService = new AgendamentoService(em);

        configurarJanela();
    }

    private void configurarJanela() {
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel panelTopo = new JPanel();
        panelTopo.setBounds(10, 10, 460, 60);
        panelTopo.setLayout(new BoxLayout(panelTopo, BoxLayout.Y_AXIS));
        add(panelTopo);

        JLabel lblTitulo = new JLabel("Bem-vindo ao menu de Agendamento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTopo.add(lblTitulo);

        JLabel lblEmail = new JLabel("E-mail vinculado: " + usuario.getEmail(), SwingConstants.CENTER);
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTopo.add(lblEmail);

        JPanel panelCentro = new JPanel();
        panelCentro.setBounds(50, 80, 380, 160);
        panelCentro.setLayout(new GridBagLayout());
        add(panelCentro);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes de data e hora
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatterService());
        datePicker.setPreferredSize(new Dimension(130, 30));

        JSpinner spinnerHora = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
        spinnerHora.setPreferredSize(new Dimension(50, 25));

        JSpinner spinnerMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 30, 30));
        spinnerMinuto.setPreferredSize(new Dimension(50, 25));

        JPanel linhaDataHora = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        linhaDataHora.add(datePicker);
        linhaDataHora.add(new JLabel("Hora:"));
        linhaDataHora.add(spinnerHora);
        linhaDataHora.add(new JLabel("Min:"));
        linhaDataHora.add(spinnerMinuto);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentro.add(linhaDataHora, gbc);

        JButton btnConfirmar = new JButton("Confirmar");
        gbc.gridy = 1;
        panelCentro.add(btnConfirmar, gbc);

        JLabel lblHorarios = new JLabel("Horários disponíveis: 10:00 - 17:00");
        gbc.gridy = 2;
        panelCentro.add(lblHorarios, gbc);

        JLabel lblResultado = new JLabel("Data selecionada:");
        gbc.gridy = 3;
        panelCentro.add(lblResultado, gbc);

        // Ação do botão
        btnConfirmar.addActionListener(e -> {
            try {
                Date dataSelecionada = (Date) datePicker.getModel().getValue();
                if (dataSelecionada == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione uma data.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int hora = (int) spinnerHora.getValue();
                int minuto = (int) spinnerMinuto.getValue();

                LocalDateTime dataHora = dataSelecionada.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(hora, minuto);

                Optional<AgendamentoEntity> existente = agendamentoService.procurarAgendamentoPorHorario(dataHora);
                if (existente.isPresent()) {
                    JOptionPane.showMessageDialog(this, "Já existe reunião neste horário!", "Conflito", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DayOfWeek diaSemanaEnum = dataHora.getDayOfWeek();
                String diaSemana = diaSemanaEnum.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toLowerCase();

                Optional<DisponibilidadeEntity> opt = disponibilidadeService.buscarPorDiaSemana(diaSemana);

                if (opt.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: sem disponibilidade para " + diaSemana,
                            "Disponibilidade",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                DisponibilidadeEntity disp = opt.get();

                if (disp.isBloqueado()) {
                    JOptionPane.showMessageDialog(this,
                            "Dia bloqueado para agendamento.",
                            "Indisponível",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!disp.isDiaTodo()) {
                    LocalTime horaAgendada = dataHora.toLocalTime();
                    if (horaAgendada.isBefore(disp.getHoraInicio()) || horaAgendada.isAfter(disp.getHoraFim())) {
                        JOptionPane.showMessageDialog(this,
                                "Horário fora da faixa permitida: " +
                                        disp.getHoraInicio() + " até " + disp.getHoraFim(),
                                "Fora da faixa",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Agendar
                AgendamentoEntity agendamento = new AgendamentoEntity();
                agendamento.setCliente(usuario);
                agendamento.setDataHora(dataHora);
                agendamento.setStatus("agendado");

                agendamentoService.salvarAgendamento(agendamento);

                // Mensagem de sucesso com detalhes
                String resumo = String.format(
                        "Agendado com sucesso!\n\nNome: %s\nEmail: %s\nData: %s",
                        usuario.getNome(),
                        usuario.getEmail(),
                        dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"))
                );

                JOptionPane.showMessageDialog(this,
                        resumo,
                        "Agendamento concluído",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

    }

    public static void mostrar(EntityManager em, UsuariosEntity usuario) {
        SwingUtilities.invokeLater(() -> new AgendamentoSwing(em, usuario).setVisible(true));
    }
}
