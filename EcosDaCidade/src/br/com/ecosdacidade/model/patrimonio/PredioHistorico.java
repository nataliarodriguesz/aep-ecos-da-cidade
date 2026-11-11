package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class PredioHistorico extends Patrimonio {
    private int anoConstrucao;
    private String estiloArquitetonico;

    public PredioHistorico(Morador moradorResponsavel,
                           String nome, String descricao,
                           EstadoConservacao estadoConservacao,
                           Endereco endereco,
                           int anoConstrucao,
                           String estiloArquitetonico) {

        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        this.anoConstrucao = anoConstrucao;
        this.estiloArquitetonico = estiloArquitetonico;
    }

    public int calcularIdade() {
        int anoAtual = java.time.Year.now().getValue();
        return  anoAtual - anoConstrucao;
    }

    @Override
    public String gerarDescricao() {
        return """
               -------------------------------------------
               ID: %d | %s      
               -------------------------------------------
               Categoria: %s
               Ano de construção: %d
               Estilo arquitetônico: %s
               Conservação: %s
               Endereco: %s
               -------------------------------------------"""
               .formatted(
                  id,
                  nome,
                  getClass().getSimpleName(),
                  anoConstrucao,
                  estiloArquitetonico,
                  estadoConservacao,
                  endereco.toString()
               );
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
