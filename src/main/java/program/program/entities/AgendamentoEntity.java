package org.example.entities;
import javax.persistence.*;
import java.time.LocalDateTime;

public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "nome")
    private String nome;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // CONSTRUTORES
    public AgendamentoEntity() {}

    public AgendamentoEntity(String nome, String telefone, String email, LocalDateTime dataHora) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataHora = dataHora;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "AgendamentoEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", dataHora=" + dataHora +
                '}';
    }
}

