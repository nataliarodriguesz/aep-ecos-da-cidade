package br.com.ecosdacidade.model.usuario;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.SistemaPatrimonio;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.Relato;
import br.com.ecosdacidade.model.denuncia.TipoDenuncia;
import br.com.ecosdacidade.model.patrimonio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Morador extends Usuario{
    private int pontuacao;
    private List<Patrimonio> patrimoniosCadastrados;
    Scanner scanner = new Scanner(System.in);

    public Morador() {
        this.pontuacao = 0;
        this.patrimoniosCadastrados = new ArrayList<>();
    }

    // Getters e Setters
    public List<Patrimonio> getPatrimoniosCadastrados() {
        return patrimoniosCadastrados;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    // Métodos Gerais
    public void cadastrarPatrimonio(SistemaPatrimonio sistemaPatrimonio) {
        String escolhaCategoria = """
                                  Escolha a categoria:
                                  
                                  1 - Árvore histórica
                                  2 - Casa marcante
                                  3 - Comércio tradicional
                                  4 - Monumento
                                  5 - Praça histórica
                                  6 - Prédio histórico
                                  -------------------------------------------
                                  Opção: """;

        int op;
        do {
            System.out.println("-------------------------------------------");
            System.out.println("              NOVO PATRIMÔNIO              ");
            System.out.println("-------------------------------------------");
            System.out.println(escolhaCategoria);
            op = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-------------------------------------------");

            if (op < 1 || op > 6) {
                System.out.println("Opção inválida! O número deve estar entre 1 e 6.");
            }
        } while(op < 1 || op > 6);

        // Informações padrões
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Descricao: ");
        String descricao = scanner.nextLine();

        int numEstadoConservacao;
        EstadoConservacao estadoConservacao = null;

        do {
            System.out.println("-------------------------------------------");
            System.out.println("Estado conservacao: ");
            System.out.println("1. EXCELENTE");
            System.out.println("2. BOM");
            System.out.println("3. REGULAR");
            System.out.println("4. RUIM");
            System.out.println("5. CRITÍCO");
            System.out.println("-------------------------------------------");
            System.out.println("Opção: ");
            numEstadoConservacao = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-------------------------------------------");

            switch (numEstadoConservacao) {
                case 1:
                    estadoConservacao = EstadoConservacao.EXCELENTE;
                    break;
                case 2:
                    estadoConservacao = EstadoConservacao.BOM;
                    break;
                case 3:
                    estadoConservacao = EstadoConservacao.REGULAR;
                    break;
                case 4:
                    estadoConservacao = EstadoConservacao.RUIM;
                    break;
                case 5:
                    estadoConservacao = EstadoConservacao.CRITICO;
                    break;
                default:
                    System.out.println("Opção inválida! O número deve estar entre 1 e 5.");
                    break;
            }
        } while (numEstadoConservacao < 1 || numEstadoConservacao > 5);


        // Endereço
        System.out.println("Rua: ");
        String rua = scanner.nextLine();
        System.out.println("Número: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.println("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.println("Estado: ");
        String estado = scanner.nextLine();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado);
        Patrimonio patrimonio = null;

        switch (op) {
            case 1:
                System.out.println("Espécie: ");
                String especie = scanner.nextLine();
                System.out.println("Idade estimada: ");
                int idadeEstimada = scanner.nextInt();
                scanner.nextLine();

                patrimonio = new ArvoreHistorica(
                   this,
                   nome,
                   descricao,
                   estadoConservacao,
                   endereco,
                   especie,
                   idadeEstimada
                );
                break;

            case 2: {
                System.out.println("Ano de construção: ");
                int anoConstrucao = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Antigo proprietário: ");
                String antigoProprietario = scanner.nextLine();
                System.out.println("Motivo da importância: ");
                String motivoImportancia = scanner.nextLine();

                patrimonio = new CasaMarcante(
                        this,
                    nome,
                    descricao,
                    estadoConservacao,
                    endereco,
                    anoConstrucao,
                    antigoProprietario,
                    motivoImportancia
                );
                break;
            }

            case 3:
                System.out.println("Ramo de atividade: ");
                String ramoAtividade = scanner.nextLine();
                System.out.println("Ano de fundação: ");
                int anoFundacao = scanner.nextInt();
                scanner.nextLine();

                patrimonio = new ComercioTradicional(
                   this,
                   nome,
                   descricao,
                   estadoConservacao,
                   endereco,
                   ramoAtividade,
                   anoFundacao
                );
                break;

            case 4:
                System.out.println("Autor: ");
                String autor = scanner.nextLine();
                System.out.println("Material: ");
                String material = scanner.nextLine();

                patrimonio = new Monumento(
                   this,
                   nome,
                   descricao,
                   estadoConservacao,
                   endereco,
                   autor,
                   material
                );
                break;

            case 5:
                System.out.println("Área(em m²): ");
                double area = scanner.nextDouble();
                scanner.nextLine();

                patrimonio = new PracaHistorica(
                   this,
                   nome,
                   descricao,
                   estadoConservacao,
                   endereco,
                   area
                );
                break;

            case 6: {
                System.out.println("Estilo arquitetônico: ");
                String estiloArquitetonico = scanner.nextLine();
                System.out.println("Ano de construção: ");
                int anoConstrucao = scanner.nextInt();
                scanner.nextLine();

                patrimonio = new PredioHistorico(
                    this,
                    nome,
                    descricao,
                    estadoConservacao,
                    endereco,
                    anoConstrucao,
                    estiloArquitetonico
                );
                break;
            }

            default:
                System.out.println("Opção inválida!");
        }
        sistemaPatrimonio.cadastrarPatrimonio(patrimonio, this);
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public int calcularPontuacao() {
        return pontuacao;
    }

    public void criarRelato(Patrimonio patrimonio) {
        System.out.println("Título do relato: ");
        String titulo = scanner.nextLine();
        System.out.println("Conte seu relato: ");
        String conteudo = scanner.nextLine();

        Relato relato = new Relato(titulo, conteudo, this, patrimonio);
        patrimonio.adicionarRelato(relato);

        adicionarPontos(10);
    }

    public Denuncia registrarDenuncia(Patrimonio patrimonio) {
        TipoDenuncia tipo = null;
        int op;

        do {
            System.out.println("-------------------------------------------");
            System.out.println("Escolha o tipo de denúncia:");
            System.out.println("1 - Abandono");
            System.out.println("2 - Vandalismo");
            System.out.println("3 - Risco de demolição");
            System.out.println("4 - Descaracterização");
            System.out.println("5 - Risco ambiental");
            System.out.println("6 - Outro");
            System.out.println("-------------------------------------------");
            System.out.println("Opção: ");
            op = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-------------------------------------------");

            switch (op) {
                case 1:
                    tipo = TipoDenuncia.ABANDONO;
                    break;
                case 2:
                    tipo = TipoDenuncia.VANDALISMO;
                    break;
                case 3:
                    tipo = TipoDenuncia.RISCO_DE_DEMOLICAO;
                    break;
                case 4:
                    tipo = TipoDenuncia.DESCARACTERIZAÇÃO;
                    break;
                case 5:
                    tipo = TipoDenuncia.RISCO_AMBIENTAL;
                    break;
                case 6:
                    tipo = TipoDenuncia.OUTRO;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (op < 1 || op > 6);

        System.out.println("Descreva o problema:");
        String descricao = scanner.nextLine();

        Denuncia denuncia = new Denuncia(tipo, descricao, this, patrimonio);

        patrimonio.adicionarDenuncia(denuncia);

        return denuncia;
    }
}
