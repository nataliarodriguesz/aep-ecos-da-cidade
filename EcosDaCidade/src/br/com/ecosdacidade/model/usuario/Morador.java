package br.com.ecosdacidade.model.usuario;

import br.com.ecosdacidade.model.relato.Relato;
import br.com.ecosdacidade.model.relato.StatusRelato;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.denuncia.StatusDenuncia;
import br.com.ecosdacidade.model.denuncia.TipoDenuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Morador extends Usuario {
    private static final int PONTOS_RELATO = 10;

    private int pontuacao;
    private final List<Patrimonio> patrimoniosCadastrados = new ArrayList<>();
    private final List<Relato> relatos = new ArrayList<>();
    private final List<Denuncia> denuncias = new ArrayList<>();

    public Morador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public List<Patrimonio> getPatrimoniosCadastrados() {
        return Collections.unmodifiableList(patrimoniosCadastrados);
    }

    public List<Relato> getRelatos() {
        return Collections.unmodifiableList(relatos);
    }

    public List<Denuncia> getDenuncias() {
        return Collections.unmodifiableList(denuncias);
    }

    public void adicionarPatrimonio(Patrimonio patrimonio) {
        patrimoniosCadastrados.add(patrimonio);
    }

    public void adicionarPontos(int pontos) {
        if (pontos <= 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        this.pontuacao += pontos;
    }

    public Relato criarRelato(String titulo, String conteudo, Patrimonio patrimonio) {
        for (Relato r : relatos) {
            if (r.getPatrimonio() == patrimonio && r.getStatus() == StatusRelato.PUBLICADO) {
                throw new IllegalStateException("Você já publicou um relato neste patrimônio.");
            }
        }

        if (!patrimonio.estaAprovado()) {
            throw new IllegalStateException("Só é possível relatar sobre patrimônio aprovado");
        }

        Relato relato = new Relato(titulo, conteudo, this, patrimonio);
        patrimonio.adicionarRelato(relato);
        relatos.add(relato);
        adicionarPontos(PONTOS_RELATO);
        return relato;
    }

    public Denuncia registrarDenuncia(TipoDenuncia tipo, String descricao, Patrimonio patrimonio) {
        for (Denuncia d : denuncias) {
            if (d.getPatrimonio() == patrimonio && d.getStatus() == StatusDenuncia.PENDENTE) {
                throw new IllegalStateException("Você já tem uma denúncia pendente para este patrimônio.");
            }
        }

        if (!patrimonio.estaAprovado()) {
            throw new IllegalStateException("Só é possível denunciar patrimônio aprovado");
        }

        Denuncia denuncia = new Denuncia(tipo, descricao, this, patrimonio);
        patrimonio.adicionarDenuncia(denuncia);
        denuncias.add(denuncia);
        return denuncia;
    }
}