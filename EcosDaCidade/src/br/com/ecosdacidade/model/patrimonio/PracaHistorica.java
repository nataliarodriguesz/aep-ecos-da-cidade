package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class PracaHistorica extends Patrimonio {
    private final double area;

    public PracaHistorica(Morador moradorResponsavel, String nome, String descricao,
                          EstadoConservacao estadoConservacao, Endereco endereco,
                          double area) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        if (area <= 0) {
            throw new IllegalArgumentException("Área deve ser maior que zero");
        }

        this.area = area;
    }

    @Override
    protected String detalhesEspecificos() {
        return "Área: %.2f m²".formatted(area);
    }
}