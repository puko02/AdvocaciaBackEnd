package org.example.functions;

import org.example.models.*;
import org.example.services.*;

import javax.persistence.EntityManager;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.Locale;
import java.util.Scanner;

public class Agendamento {

    public static void fazerAgendamento(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        AgendamentoService agendamentoService = new AgendamentoService(em);
        DisponibilidadeService disponibilidadeService = new DisponibilidadeService(em);
        UsuarioService usuarioService = new UsuarioService(em);

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

        DayOfWeek diaSemanaEnum = dataHora.getDayOfWeek();
        String diaSemana = diaSemanaEnum.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toLowerCase();

        Optional<DisponibilidadeEntity> opt = disponibilidadeService.buscarPorDiaSemana(diaSemana);

        if (!opt.isPresent()) {
            System.out.println("Não há disponibilidade cadastrada para " + diaSemana);
            return;
        }

        DisponibilidadeEntity disp = opt.get();

        if (disp.isBloqueado()) {
            System.out.println("Dia bloqueado para agendamento.");
            return;
        }

        if (!disp.isDiaTodo()) {
            LocalTime horaAgendada = dataHora.toLocalTime();
            if (horaAgendada.isBefore(disp.getHoraInicio()) || horaAgendada.isAfter(disp.getHoraFim())) {
                System.out.println("Horário fora da faixa permitida: " +
                        disp.getHoraInicio() + " até " + disp.getHoraFim());
                return;
            }
        }

        UsuariosEntity usuario;
        Optional<UsuariosEntity> existente = usuarioService.buscarPorEmail(email);

        if (existente.isPresent()) {
            usuario = existente.get();
            System.out.println("Usuário já cadastrado, reutilizando cadastro existente.");
        } else {
            usuario = new UsuariosEntity();
            usuario.setNome(nome);
            usuario.setTelefone(telefone);
            usuario.setEmail(email);
            usuario.setSenha("");
            usuario.setAdmin(false);

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();

            System.out.println("Novo usuário cadastrado.");
        }

        AgendamentoEntity agendamento = new AgendamentoEntity();
        agendamento.setCliente(usuario);
        agendamento.setDataHora(dataHora);
        agendamento.setStatus("agendado");

        agendamentoService.salvarAgendamento(agendamento);

        System.out.println("\nAgendamento salvo com sucesso!");
        System.out.println("Resumo:");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Telefone: " + usuario.getTelefone());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Data: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")));
    }
}
