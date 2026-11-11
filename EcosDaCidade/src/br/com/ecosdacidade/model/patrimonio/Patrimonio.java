package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.*;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Patrimonio {
    private   Morador moradorResponsavel;
    protected int id;
    protected String nome;
    protected String descricao;
    protected LocalDate dataCadastro;
    protected EstadoConservacao estadoConservacao;
    protected StatusAprovacao statusAprovacao;
    protected Endereco endereco;

    private List<Relato> relatos = new ArrayList<>();
    private List<Denuncia> denuncias = new ArrayList<>();

    public Patrimonio(Morador moradorResponsavel,
                      String nome, String descricao,
                      EstadoConservacao estadoConservacao,
                      Endereco endereco) {

        if (moradorResponsavel == null) throw new IllegalArgumentException("Morador responsável é obrigatório");
        this.moradorResponsavel = moradorResponsavel;

        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = LocalDate.now();
        this.endereco = endereco;
        this.estadoConservacao = estadoConservacao;
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
