package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.Year;

public class ComercioTradicional extends Patrimonio {
    private final String ramoAtividade;
    private final int anoFundacao;

    public ComercioTradicional(Morador moradorResponsavel, String nome, String descricao,
                               EstadoConservacao estadoConservacao, Endereco endereco,
                               String ramoAtividade, int anoFundacao) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        if (ramoAtividade == null || ramoAtividade.isBlank()) {
            throw new IllegalArgumentException("Ramo de atividade é obrigatório");
        }
        validarAno(anoFundacao);

        this.ramoAtividade = ramoAtividade;
        this.anoFundacao = anoFundacao;
    }

    public int calcularTempoFuncionamento() {
        return Year.now().getValue() - anoFundacao;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
               Ramo de atividade: %s
               Ano de fundação: %d
               Tempo de funcionamento: %d anos"""
                .formatted(ramoAtividade, anoFundacao, calcularTempoFuncionamento());
    }
}