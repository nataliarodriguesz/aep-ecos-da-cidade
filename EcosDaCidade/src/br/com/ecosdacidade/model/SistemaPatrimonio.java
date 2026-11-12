package br.com.ecosdacidade.model;

import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.denuncia.StatusDenuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.patrimonio.StatusAprovacao;

import java.util.ArrayList;
import java.util.List;

public class SistemaPatrimonio {
    private final List<Patrimonio> patrimonios = new ArrayList<>();

    public void cadastrarPatrimonio(Patrimonio patrimonio) {
        patrimonios.add(patrimonio);
        patrimonio.getMoradorResponsavel().adicionarPatrimonio(patrimonio);
    }

    public List<Patrimonio> listarPatrimoniosAprovados() {
        return patrimonios.stream().filter(Patrimonio::estaAprovado).toList();
    }

    public List<Patrimonio> listarPatrimoniosPendentes() {
        return patrimonios.stream()
                .filter(p -> p.getStatusAprovacao() == StatusAprovacao.PENDENTE)
                .toList();
    }

    public List<Denuncia> listarDenunciasPendentes() {
        List<Denuncia> pendentes = new ArrayList<>();
        for (Patrimonio patrimonio : patrimonios) {
            for (Denuncia denuncia : patrimonio.getDenuncias()) {
                if (denuncia.getStatus() == StatusDenuncia.PENDENTE) {
                    pendentes.add(denuncia);
                }
            }
        }
        return pendentes;
    }

    public Patrimonio buscarAprovadoPorId(int id) {
        for (Patrimonio patrimonio : listarPatrimoniosAprovados()) {
            if (patrimonio.getId() == id) {
                return patrimonio;
            }
        }
        return null;
    }

    public Patrimonio buscarPendentePorId(int id) {
        for (Patrimonio patrimonio : listarPatrimoniosPendentes()) {
            if (patrimonio.getId() == id) {
                return patrimonio;
            }
        }
        return null;
    }

    public Denuncia buscarDenunciaPendentePorId(int id) {
        for (Denuncia denuncia : listarDenunciasPendentes()) {
            if (denuncia.getId() == id) {
                return denuncia;
            }
        }
        return null;
    }
}