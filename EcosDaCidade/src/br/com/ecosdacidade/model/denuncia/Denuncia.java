package br.com.ecosdacidade.model.denuncia;

import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.LocalDateTime;

public class Denuncia {
    private static int proximoId = 1;

    private int id;
    private TipoDenuncia tipo;
    private String descricao;
    private LocalDateTime dataRegistro;
    private StatusDenuncia status;
    private Morador moradorResponsavel;
    private Patrimonio patrimonio;

    public Denuncia(TipoDenuncia tipo, String descricao, Morador autor, Patrimonio patrimonio) {
        this.id = proximoId++;
        this.tipo = tipo;
        this.descricao = descricao;
        this.moradorResponsavel = autor;
        this.patrimonio = patrimonio;
        this.dataRegistro = LocalDateTime.now();
        this.status = StatusDenuncia.PENDENTE;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public TipoDenuncia getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Morador getMoradorResponsavel() {
        return moradorResponsavel;
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public StatusDenuncia getStatus() {
        return status;
    }

    public void aprovar() {
        if (status != StatusDenuncia.PENDENTE) throw new IllegalStateException("Denúncia não está pendente");
        status = StatusDenuncia.PUBLICADA;
    }
    public void rejeitar() {
        if (status != StatusDenuncia.PENDENTE) throw new IllegalStateException("Denúncia não está pendente");
        status = StatusDenuncia.REJEITADA;
    }
    public void remover() {
        if (status != StatusDenuncia.PUBLICADA) throw new IllegalStateException("Denúncia não está publicada");
        status = StatusDenuncia.REMOVIDA;
    }

    public void marcarComoRemovida() {
        status = StatusDenuncia.REMOVIDA;
    }
}