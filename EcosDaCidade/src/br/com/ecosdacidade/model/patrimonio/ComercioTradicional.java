package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class ComercioTradicional extends Patrimonio {
    private String ramoAtividade;
    private int anoFundacao;

    public ComercioTradicional(Morador moradorResponsavel,
                               String nome, String descricao,
                               EstadoConservacao estadoConservacao,
                               Endereco endereco,
                               String ramoAtividade,
                               int anoFundacao) {

        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        this.ramoAtividade = ramoAtividade;
        this.anoFundacao = anoFundacao;
    }

    public int calcularTempoFuncionamento() {
        int anoAtual = java.time.Year.now().getValue();
        return anoAtual - anoFundacao;
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
