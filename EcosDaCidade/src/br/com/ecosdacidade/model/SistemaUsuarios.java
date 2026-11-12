package br.com.ecosdacidade.model;

import br.com.ecosdacidade.model.usuario.Administrador;
import br.com.ecosdacidade.model.usuario.Morador;
import br.com.ecosdacidade.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SistemaUsuarios {
    private final List<Usuario> usuarios = new ArrayList<>();

    public SistemaUsuarios() {
        // Administrador pré-cadastrado (ninguém cria conta de admin pelo menu)
        usuarios.add(new Administrador("Administrador", "admin@ecosdacidade.com", "admin123"));
    }

    public Morador cadastrarMorador(String nome, String email, String senha) {
        if (buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Já existe uma conta com esse e-mail.");
        }
        Morador morador = new Morador(nome, email, senha);
        usuarios.add(morador);
        return morador;
    }

    public Morador autenticarMorador(String email, String senha) {
        if (buscarPorEmail(email) instanceof Morador morador && morador.verificarSenha(senha)) {
            return morador;
        }
        throw new IllegalArgumentException("E-mail ou senha incorretos.");
    }

    public Administrador autenticarAdministrador(String email, String senha) {
        if (buscarPorEmail(email) instanceof Administrador admin && admin.verificarSenha(senha)) {
            return admin;
        }
        throw new IllegalArgumentException("E-mail ou senha incorretos.");
    }

    private Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }
        String procurado = email.trim().toLowerCase();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(procurado)) {
                return usuario;
            }
        }
        return null;
    }
}