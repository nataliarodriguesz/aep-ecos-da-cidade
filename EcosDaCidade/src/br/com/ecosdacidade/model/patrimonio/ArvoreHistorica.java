package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class ArvoreHistorica extends Patrimonio {
    private final String especie;
    private final int idadeEstimada;

    public ArvoreHistorica(Morador moradorResponsavel, String nome, String descricao,
                           EstadoConservacao estadoConservacao, Endereco endereco,
                           String especie, int idadeEstimada) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("Espécie é obrigatória");
        }
        if (idadeEstimada <= 0) {
            throw new IllegalArgumentException("Idade estimada deve ser maior que zero");
        }

        this.especie = especie;
        this.idadeEstimada = idadeEstimada;
    }

    public boolean isCentenaria() {
        return idadeEstimada >= 100;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
               Espécie: %s
               Idade estimada: %d anos"""
                .formatted(especie, idadeEstimada);
    }
}