package org.example.model;

import java.security.SecureRandom;
import javax.persistence.*;

@Entity
@Table (name = "usuario")
public class UsuariosEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        private Long id;

        private String telefone;

        @Column (nullable = true)
        private String nome;

        @Column(unique = true, nullable = false)
        private String email;

        private String senha;

        @Column(name = "is_admin")
        private boolean isAdmin;

        // Getters e Setters
        public Long getId() { return id; }

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

        public class CodigoConfirmacao {
                private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                private static final int LENGTH = 6;
                private static final SecureRandom random = new SecureRandom();

                public static String gerarCodigo() {
                        StringBuilder sb = new StringBuilder(LENGTH);
                        for (int i = 0; i < LENGTH; i++) {
                                sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
                        }
                        return sb.toString();
                }
        }

}
