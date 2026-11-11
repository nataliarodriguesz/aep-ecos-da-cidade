package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class ArvoreHistorica extends Patrimonio {
    private String especie;
    private int idadeEstimada;

    public ArvoreHistorica(Morador moradorResponsavel,
                           String nome, String descricao,
                           EstadoConservacao estadoConservacao,
                           Endereco endereco,
                           String especie,
                           int idadeEstimada
                           ) {

        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        this.especie = especie;
        this.idadeEstimada = idadeEstimada;
    }

    public boolean eCentenaria() {
        if (idadeEstimada >= 100) {
            return true;
        }

        return false;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
           Espécie: %s
           Idade estimada: %d anos""".formatted(especie, idadeEstimada);
    }
}
