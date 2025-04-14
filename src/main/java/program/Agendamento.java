package org.example;

import program.entities.AgendamentoEntity;
import program.entities.DisponibilidadeEntity;
import program.services.AgendamentoService;
import program.services.DisponibilidadeService;

import javax.persistence.EntityManager;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class Agendamento {

    public static void fazerAgendamento(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        AgendamentoService agendamentoService = new AgendamentoService(em);
        DisponibilidadeService disponibilidadeService = new DisponibilidadeService(em);

        System.out.println("\n--- AGENDAMENTO ---");

        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite seu telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Digite seu email: ");
        String email = sc.nextLine();

        System.out.print("Digite o dia do agendamento (dd/MM/yyyy): ");
        String dia = sc.nextLine();

        System.out.print("Digite o horário (HH:mm): ");
        String horario = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora;

        try {
            dataHora = LocalDateTime.parse(dia + " " + horario, formatter);
        } catch (Exception e) {
            System.out.println("Data ou hora inválida!");
            return;
        }

        // Dia da semana
        DayOfWeek diaSemanaEnum = dataHora.getDayOfWeek();
        String diaSemana = diaSemanaEnum.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toLowerCase();

        // Verificar disponibilidade
        Optional<DisponibilidadeEntity> opt = disponibilidadeService.buscarPorDiaSemana(diaSemana);

        if (opt.isEmpty()) {
            System.out.println("Não há disponibilidade cadastrada para " + diaSemana);
            return;
        }

        DisponibilidadeEntity disp = opt.get();

        if (disp.isBloqueado()) {
            System.out.println("Dia bloqueado para agendamento.");
            return;
        }

        if (!disp.isDi  aTodo()) {
            LocalTime horaAgendada = dataHora.toLocalTime();
            if (horaAgendada.isBefore(disp.getHoraInicio()) || horaAgendada.isAfter(disp.getHoraFim())) {
                System.out.println("Horário fora da faixa permitida: " +
                        disp.getHoraInicio() + " até " + disp.getHoraFim());
                return;
            }
        }

        // Agendar
        AgendamentoEntity agendamento = new AgendamentoEntity(nome, telefone, email, dataHora);
        agendamentoService.salvarAgendamento(agendamento);

        System.out.println("\nAgendamento salvo com sucesso!");
        System.out.println("Resumo:");
        System.out.println("Nome: " + nome);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Data: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")));
    }
}
