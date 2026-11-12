package br.com.ecosdacidade.model.patrimonio;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.usuario.Morador;

import java.time.Year;

public class CasaMarcante extends Patrimonio {
    private final int anoConstrucao;
    private final String antigoProprietario;
    private final String motivoImportancia;

    public CasaMarcante(Morador moradorResponsavel, String nome, String descricao,
                        EstadoConservacao estadoConservacao, Endereco endereco,
                        int anoConstrucao, String antigoProprietario, String motivoImportancia) {
        super(moradorResponsavel, nome, descricao, estadoConservacao, endereco);

        validarAno(anoConstrucao);
        if (motivoImportancia == null || motivoImportancia.isBlank()) {
            throw new IllegalArgumentException("Motivo da importância é obrigatório");
        }

        this.anoConstrucao = anoConstrucao;
        this.antigoProprietario = antigoProprietario;
        this.motivoImportancia = motivoImportancia;
    }

    public int calcularIdade() {
        return Year.now().getValue() - anoConstrucao;
    }

    @Override
    protected String detalhesEspecificos() {
        return """
               Ano de construção: %d
               Idade: %d anos
               Importância: %s
               Antigo proprietário: %s"""
                .formatted(anoConstrucao, calcularIdade(), motivoImportancia, antigoProprietario);
    }
}