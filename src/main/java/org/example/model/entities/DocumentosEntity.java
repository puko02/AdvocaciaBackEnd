package org.example.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "documento")
public class DocumentosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_documento")
    private Long id;

    private String nome;
    private String tipo;

    @Column(name = "caminho_arquivo")
    private String caminhoArquivo;

    @ManyToMany(mappedBy = "documentos")
    private List<AgendamentoEntity> agendamentos;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public List<AgendamentoEntity> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoEntity> agendamentos) {
        this.agendamentos = agendamentos;
    }

}
