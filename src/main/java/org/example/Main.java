package org.example;

import javax.persistence.EntityManager;

import org.example.functions.MenuPrincipal;
import org.example.models.UsuariosEntity;
import org.example.repositories.CustomizerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        em.getTransaction().begin();

        UsuariosEntity cliente = new UsuariosEntity();
        MenuPrincipal.mostrar(em);
        em.getTransaction().commit();
        em.close();
    }
}