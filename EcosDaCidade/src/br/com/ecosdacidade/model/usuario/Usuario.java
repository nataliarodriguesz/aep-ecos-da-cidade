package br.com.ecosdacidade.model.usuario;

import java.time.LocalDate;

public abstract class Usuario {
    private static int proximoId = 1;   // TEMPORÁRIO: apague quando usar banco (auto incremento)

    private final int id;
    private String nome;
    private final String email;
    private String senha;
    private final LocalDate dataCadastro;

    protected Usuario(String nome, String email, String senha) {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);

        this.id = proximoId++;
        this.nome = nome.trim();
        this.email = email.trim().toLowerCase();
        this.senha = senha;
        this.dataCadastro = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public boolean verificarSenha(String tentativa) {
        return senha.equals(tentativa);
    }

    public void alterarSenha(String senhaAtual, String novaSenha) {
        if (!verificarSenha(senhaAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }
        validarSenha(novaSenha);
        this.senha = novaSenha;
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
    }

    private static void validarEmail(String email) {
        if (email == null || !email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    private static void validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
    }
}