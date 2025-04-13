package program.functions;
import program.models.AgendamentoEntity;
//import program.models.UsuariosEntity;
//import program.repositories.AgendamentoRepository;
import program.repositories.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;


public class VisualizarAgendamentosFunction {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //AgendamentoRepository agendamentoRepo = new AgendamentoRepository();

        boolean sair = false;

        while (!sair) {

            System.out.println("\n========== Agendamentos ==========");
            System.out.println("1. Ver todos os Agendamentos");
            System.out.println("2. Sair");
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

                            for (AgendamentoEntity a : agendamentos) {
                            System.out.println("ID: " + a.getId());
                            System.out.println("Cliente: " + a.getCliente().getNome());
                            System.out.println("Data/Hora: " + a.getDataHora());
                            System.out.println("Descricao: " + a.getDescricao());
                            System.out.println("Documentos: " + a.getDocumentos());
                            System.out.println("Status do Agendamento: " + a.getStatus());
                            System.out.println("-------------------------");
                            }

                        }

                    } finally {
                        em.close();
                    }
                    break;
                case 2:
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
