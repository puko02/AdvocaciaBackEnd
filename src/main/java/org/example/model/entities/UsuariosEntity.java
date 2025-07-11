package org.example.model.entities;

import javax.persistence.*;

@Entity
@Table (name = "usuario")
public class UsuariosEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        private Long id;

        @Column (nullable = true)
        private String nome;

        private String telefone;

        @Column(unique = true, nullable = false)
        private String email;

        private String senha;

        @Column(name = "is_admin")
        private boolean isAdmin;

        @Column(name = "isactive")
        private boolean isActive;

        @Column(name = "confirmcode")
        private String confirmCode;

        // Getters e Setters
        public Long getId() { return id; }

        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }

        public String getConfirmcode() { return confirmCode; }
        public void setConfirmcode(String confirmCode) { this.confirmCode = confirmCode; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public boolean isAdmin() {return isAdmin;}
        public void setAdmin(boolean admin) { isAdmin = admin; }

        public String getTelefone() { return telefone; }
        public void setTelefone(String telefone) { this.telefone = telefone; }

        @Override
        public String toString() {
                return "UsuariosEntity{" +
                        "id=" + id +
                        ", telefone='" + telefone + '\'' +
                        ", nome='" + nome + '\'' +
                        ", email='" + email + '\'' +
                        ", senha='" + senha + '\'' +
                        ", isAdmin=" + isAdmin +
                        '}';
        }



}
