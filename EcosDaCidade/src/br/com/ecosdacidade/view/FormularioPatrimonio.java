package br.com.ecosdacidade.view;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.patrimonio.*;
import br.com.ecosdacidade.model.usuario.Morador;

public class FormularioPatrimonio {
    private final Entrada entrada;

    public FormularioPatrimonio(Entrada entrada) {
        this.entrada = entrada;
    }

    public Patrimonio ler(Morador morador) {
        System.out.println("=== NOVO PATRIMÔNIO ===");

        int categoria = entrada.lerOpcao("Escolha a categoria:",
                "Árvore histórica", "Casa marcante", "Comércio tradicional",
                "Monumento", "Praça histórica", "Prédio histórico");

        String nome = entrada.lerTexto("Nome: ");
        String descricao = entrada.lerTexto("Descrição: ");
        EstadoConservacao estado = entrada.lerEnum("Estado de conservação:", EstadoConservacao.values());
        Endereco endereco = lerEndereco();

        return switch (categoria) {
            case 1 -> {
                String especie = entrada.lerTexto("Espécie: ");
                int idade = entrada.lerInt("Idade estimada: ");
                yield new ArvoreHistorica(morador, nome, descricao, estado, endereco, especie, idade);
            }
            case 2 -> {
                int ano = entrada.lerInt("Ano de construção: ");
                String proprietario = entrada.lerTexto("Antigo proprietário: ");
                String motivo = entrada.lerTexto("Motivo da importância: ");
                yield new CasaMarcante(morador, nome, descricao, estado, endereco, ano, proprietario, motivo);
            }
            case 3 -> {
                String ramo = entrada.lerTexto("Ramo de atividade: ");
                int ano = entrada.lerInt("Ano de fundação: ");
                yield new ComercioTradicional(morador, nome, descricao, estado, endereco, ramo, ano);
            }
            case 4 -> {
                String autor = entrada.lerTexto("Autor: ");
                String material = entrada.lerTexto("Material: ");
                yield new Monumento(morador, nome, descricao, estado, endereco, autor, material);
            }
            case 5 -> {
                double area = entrada.lerDouble("Área (em m²): ");
                yield new PracaHistorica(morador, nome, descricao, estado, endereco, area);
            }
            default -> {
                String estilo = entrada.lerTexto("Estilo arquitetônico: ");
                int ano = entrada.lerInt("Ano de construção: ");
                yield new PredioHistorico(morador, nome, descricao, estado, endereco, ano, estilo);
            }
        };
    }

    private Endereco lerEndereco() {
        String rua = entrada.lerTexto("Rua: ");
        int numero = entrada.lerInt("Número: ");
        String bairro = entrada.lerTexto("Bairro: ");
        String cidade = entrada.lerTexto("Cidade: ");
        String estado = entrada.lerTexto("Estado: ");
        return new Endereco(rua, numero, bairro, cidade, estado);
    }
}