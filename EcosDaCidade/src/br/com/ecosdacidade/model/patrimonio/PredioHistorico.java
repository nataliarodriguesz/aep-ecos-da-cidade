package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.Year;

public class PredioHistorico extends Patrimonio {
    private final int anoConstrucao;
    private final String estiloArquitetonico;

    public PredioHistorico(Morador moradorResponsavel, String nome, String descricao,
                           EstadoConservacao estadoConservacao, Endereco endereco,
                           int anoConstrucao, String estiloArquitetonico) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        validarAno(anoConstrucao);
        if (estiloArquitetonico == null || estiloArquitetonico.isBlank()) {
            throw new IllegalArgumentException("Estilo arquitetônico é obrigatório");
        }

        this.anoConstrucao = anoConstrucao;
        this.estiloArquitetonico = estiloArquitetonico;
    }

    public int calcularIdade() {
        return Year.now().getValue() - anoConstrucao;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
               Ano de construção: %d
               Idade: %d anos
               Estilo arquitetônico: %s"""
                .formatted(anoConstrucao, calcularIdade(), estiloArquitetonico);
    }
}