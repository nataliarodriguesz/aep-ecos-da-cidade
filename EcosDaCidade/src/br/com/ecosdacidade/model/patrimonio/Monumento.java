package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class Monumento extends Patrimonio {
    private final String autor;
    private final String material;

    public Monumento(Morador moradorResponsavel, String nome, String descricao,
                     EstadoConservacao estadoConservacao, Endereco endereco,
                     String autor, String material) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Material é obrigatório");
        }

        this.autor = autor;
        this.material = material;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
               Autor: %s
               Material: %s"""
                .formatted(autor, material);
    }
}