package org.example;

import javax.persistence.EntityManager;

import org.example.model.entities.UsuariosEntity;
import org.example.view.viewGUI.AgendamentoSwing;
import org.example.view.viewGUI.MenuPrincipal;
import org.example.model.config.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        MenuPrincipal.mostrar(em);
    }
}