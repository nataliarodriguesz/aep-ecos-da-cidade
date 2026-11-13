package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.*;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.denuncia.StatusDenuncia;
import br.com.ecosdacidade.model.usuario.Morador;
import br.com.ecosdacidade.relato.Relato;
import br.com.ecosdacidade.relato.StatusRelato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Patrimonio {
    private static int proximoId = 1;   // TEMPORÁRIO: apague quando usar banco

    private   Morador moradorResponsavel;
    private final int id;
    protected String nome;
    protected String descricao;
    protected LocalDate dataCadastro;
    protected EstadoConservacao estadoConservacao;
    protected StatusAprovacao statusAprovacao;
    protected Endereco endereco;

    private List<Relato> relatos = new ArrayList<>();
    private List<Denuncia> denuncias = new ArrayList<>();

    public Patrimonio(Morador moradorResponsavel, String nome, String descricao,
                      EstadoConservacao estadoConservacao, Endereco endereco) {

        Objects.requireNonNull(moradorResponsavel, "Morador responsável é obrigatório");
        Objects.requireNonNull(estadoConservacao, "Estado de conservação é obrigatório");
        Objects.requireNonNull(endereco, "Endereço é obrigatório");
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.id = proximoId++;
        this.moradorResponsavel = moradorResponsavel;
        this.nome = nome;
        this.descricao = descricao;
        this.estadoConservacao = estadoConservacao;
        this.endereco = endereco;
        this.dataCadastro = LocalDate.now();
        this.statusAprovacao = StatusAprovacao.PENDENTE;
    }
    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public Morador getMoradorResponsavel() {
        return moradorResponsavel;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public EstadoConservacao getEstadoConservacao() {
        return estadoConservacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public List<Relato> getRelatos() {
        return relatos;
    }

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public List<Relato> getRelatosPublicados() {
        return relatos.stream()
                .filter(r -> r.getStatus() == StatusRelato.PUBLICADO)
                .toList();
    }

    public List<Denuncia> getDenunciasPublicadas() {
        return denuncias.stream()
                .filter(d -> d.getStatus() == StatusDenuncia.PUBLICADA)
                .toList();
    }

    // Métodos Gerais
    public  void alterarEstadoConservacao(EstadoConservacao novoEstado) {
        this.estadoConservacao = novoEstado;
    }

    public String getDataCadastroFormatada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataCadastro.format(formato);
    }

    protected static void validarAno(int ano) {
        if (ano <= 0 || ano > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Ano inválido: " + ano);
        }
    }

    public void aprovar() {
        if (statusAprovacao != StatusAprovacao.PENDENTE) {
            throw new IllegalStateException("Só é possível aprovar patrimônio pendente");
        }
        statusAprovacao = StatusAprovacao.APROVADO;
    }

    public void rejeitar() {
        if (statusAprovacao != StatusAprovacao.PENDENTE) {
            throw new IllegalStateException("Só é possível rejeitar patrimônio pendente");
        }
        statusAprovacao = StatusAprovacao.REJEITADO;
    }

    public void remover() {
        if (statusAprovacao != StatusAprovacao.APROVADO) {
            throw new IllegalStateException("Só é possível remover patrimônio aprovado");
        }
        statusAprovacao = StatusAprovacao.REMOVIDO;

        for (Relato relato : relatos) {
            relato.marcarComoRemovido();
        }
        for (Denuncia denuncia : denuncias) {
            denuncia.marcarComoRemovida();
        }
    }

    public  boolean estaAprovado() {
        return statusAprovacao == StatusAprovacao.APROVADO;
    }

    public void adicionarRelato(Relato relato) {
        relatos.add(relato);
    }

    public void adicionarDenuncia(Denuncia denuncia) {
        denuncias.add(denuncia);
    }

    protected abstract String detalhesEspecificos();

    public String gerarDescricao() {
        return """
           -------------------------------------------
           ID: %d | %s
           -------------------------------------------
           Categoria: %s
           %s
           Conservação: %s
           Endereço: %s
           -------------------------------------------"""
                .formatted(id, nome, getClass().getSimpleName(),
                        detalhesEspecificos(), estadoConservacao, endereco);
    }

    public String gerarDetalhes() {
        return """
           ===========================================
                      DETALHES DO PATRIMÔNIO
           ===========================================
           Nome: %s
           Descrição: %s

           %s

           Estado de conservação: %s

           Logradouro: %s, %d
           Bairro: %s
           Cidade: %s
           Estado: %s

           Categoria: %s
           Data de cadastro: %s"""
                .formatted(nome, descricao, detalhesEspecificos(), estadoConservacao,
                        endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(),
                        endereco.getCidade(), endereco.getEstado(),
                        getClass().getSimpleName(), getDataCadastroFormatada());
    }

}
