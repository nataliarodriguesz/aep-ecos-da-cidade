package br.com.ecosdacidade.model;

import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.usuario.Morador;

import java.util.ArrayList;
import java.util.List;

public class SistemaPatrimonio {
    private List<Patrimonio> patrimonios = new ArrayList<>();

    public void cadastrarPatrimonio(Patrimonio patrimonio) {
        patrimonios.add(patrimonio);
        patrimonio.getMoradorResponsavel().cadastrarPatrimonio();
    }

    public List<Patrimonio> listarPatrimoniosAprovados() {
        return patrimonios.stream().filter(Patrimonio::estaAprovado).toList();
    }
}