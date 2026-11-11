package br.com.ecosdacidade.model;

import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.LocalDateTime;

public class Relato {
    private static int proximoId = 1;

    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private Morador autor;
    private Patrimonio patrimonio;
    private StatusRelato status;

    public Relato(String titulo, String conteudo, Morador autor, Patrimonio patrimonio) {
        this.id = proximoId++;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;
        this.patrimonio = patrimonio;
        this.dataPublicacao = LocalDateTime.now();
        this.status = StatusRelato.PUBLICADO;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public StatusRelato getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public Morador getAutor() {
        return autor;
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public void remover() {
        if (status != StatusRelato.PUBLICADO) throw new IllegalStateException("Denúncia não está publicada");
        status = StatusRelato.REMOVIDO;
    }
}
