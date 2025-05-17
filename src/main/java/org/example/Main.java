package org.example;

import javax.persistence.EntityManager;

import org.example.view.MenuPrincipal;
import org.example.model.UsuariosEntity;
import org.example.control.repositories.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();

        MenuPrincipal.mostrar(em);

        em.close();
    }
}