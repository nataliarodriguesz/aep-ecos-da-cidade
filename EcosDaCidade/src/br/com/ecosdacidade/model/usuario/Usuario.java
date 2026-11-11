package br.com.ecosdacidade.model.usuario;

import java.time.LocalDate;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected LocalDate dataCadastro;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void alterarSenha(String novaSenha) {

    }
}
