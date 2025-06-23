package org.example.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nota")
public class AnotacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_nota")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private UsuariosEntity cliente;

    @Column (name = "conteudo")
    private String nota;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    public UsuariosEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuariosEntity cliente) {
        this.cliente = cliente;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }



    public Long getId() {
        return id;
    }
}
