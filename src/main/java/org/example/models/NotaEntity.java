package org.example.models;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anotacao")
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_nota")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private UsuariosEntity cliente;

    private String conteudo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    public UsuariosEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuariosEntity cliente) {
        this.cliente = cliente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
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
