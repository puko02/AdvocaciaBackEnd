package org.example.view;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class MenuAdmin {
    public static void menuAdministrador(EntityManager em){
        int selected = 1;
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("Bem vindo ao menu de Administrador\n");
            System.out.println("1 - Editar horário de atendimento do dia da semana");
            System.out.println("2 - Adicionar ou verificar notas de clientes");
            System.out.println("3 - Verificar agendamentos");
            System.out.println("4 - Editar dados de cliente");
            System.out.print  ("0 - Sair do menu do administrador\n  -> ");
            selected = sc.nextInt();
            sc.nextLine();

            switch(selected){
                case 1:
                    DisponibilidadeFunction.menuDisponibilidade();
                    break;
                case 2:
                    UsuariosSwing.abrirTela(em);
                    //UsuariosFunction.menuUser(em);
                    break;
                case 3:
                    VisuAgendSwing.abrirTela();
                    break;
                case 4:
                    MenuEditFunction.MenuEditarCliente(em);
                    break;
                case 0:
                    System.out.println("Retornando...");
                    return;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
}
