package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

public class Monumento extends Patrimonio {
    private String autor;
    private String material;

    public Monumento(Morador moradorResponsavel,
                     String nome, String descricao,
                     EstadoConservacao estadoConservacao,
                     Endereco endereco,
                     String autor,
                     String material
                     ) {

        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

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
