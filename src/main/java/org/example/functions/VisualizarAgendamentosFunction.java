package program.functions;
import org.example.models.AgendamentoEntity;
import org.example.models.UsuariosEntity;
import org.example.repositories.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class VisualizarAgendamentosFunction {

    public static void menuVizuAgendamento() {
        Scanner sc = new Scanner(System.in);
        AgendamentoRepository agendamentoRepo = new AgendamentoRepository();

        boolean sair = false;

        while (!sair) {

            System.out.println("\n========== Agendamentos ==========");
            System.out.println("1. Ver todos os Agendamentos");
            System.out.println("2. Alterar Agendamento");
            System.out.println("3. Sair");
            System.out.print("Escolha sua opcao: ");
            int opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    EntityManager em = CustomizerFactory.getEntityManager();
                    try {
                        List<AgendamentoEntity> agendamentos = em.createQuery(
                                        "FROM AgendamentoEntity", AgendamentoEntity.class)
                                .getResultList();

                        if (agendamentos.isEmpty()) {

                        System.out.println("Nenhum Agendamento Cadastrado.");

                        } else {

                        System.out.println("\n------ Agendamentos ------");

                            for (AgendamentoEntity agendamento : agendamentos) {
                                System.out.println("ID: " + agendamento.getId());
                                System.out.println("Data/Hora: " + agendamento.getDataHora());
                                System.out.println("Status do Agendamento: " + agendamento.getStatus());
                                System.out.println("Descrição: " + agendamento.getDescricao());
                                System.out.println("Documentos: " + agendamento.getDocumentos());
                                System.out.println("---------------");
                            }

                        }

                    } finally {
                        em.close();
                    }
                    break;
                case 2:
                        System.out.println("Digite o ID do Agendamento que deseja editar:");
                        Long idEdit = sc.nextLong();
                        sc.nextLine();

                        System.out.print("Digite o novo dia do agendamento (dd/MM/yyyy): ");
                        String dia = sc.nextLine();

                        System.out.print("Digite o novo horário do Agendamento (HH:mm): ");
                        String horario = sc.nextLine();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        LocalDateTime dataHora;

                        try {
                        dataHora = LocalDateTime.parse(dia + " " + horario, formatter);
                        } catch (Exception e) {
                            System.out.println("Data ou hora inválida!");
                            return;
                        }
                        System.out.println("Novo Status do Agendamento:");
                        String status = sc.nextLine();

                        System.out.println("Nova Descricao:");
                        String descricao = sc.nextLine();


                        // Atualizar no banco
                        agendamentoRepo.atualizarAgendamento(idEdit, dataHora, status, descricao);
                        System.out.println("Agendamento atualizado com sucesso!");
                    break;
                case 3:
                    sair = true;
                    System.out.println("Saindo do menu...");
                    break;
                default:
                    System.out.println("Opcao Invalida. Por favor escolha uma opcao valida e tente novamente.");
            }
        }

        sc.close();
    }
}
