package br.com.ecosdacidade.view;

import br.com.ecosdacidade.model.SistemaPatrimonio;
import br.com.ecosdacidade.model.denuncia.TipoDenuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.usuario.Morador;

public class MenuMorador {
    private final Entrada entrada;
    private final SistemaPatrimonio sistema;
    private final Morador morador;
    private final TelaPerfil telaPerfil;

    public MenuMorador(Entrada entrada, SistemaPatrimonio sistema, Morador morador) {
        this.entrada = entrada;
        this.sistema = sistema;
        this.morador = morador;
        this.telaPerfil = new TelaPerfil(entrada);
    }

    public void iniciar() {
        Tela.titulo("Olá, " + morador.getNome() + "!");
        boolean sair = false;

        while (!sair) {
            int opcao = entrada.lerOpcaoMenu("O que deseja fazer?", "Sair da conta",
                    "Início (catálogo de patrimônios)",
                    "Cadastrar patrimônio",
                    "Meu perfil");

            switch (opcao) {
                case 1 -> catalogo();
                case 2 -> cadastrarPatrimonio();
                case 3 -> perfil();
                case 0 -> sair = true;
            }
        }
        Tela.aviso("Você saiu da conta.");
    }

    // ---------- Catálogo ----------

    private void catalogo() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("CATÁLOGO DE PATRIMÔNIOS");
            TelaPatrimonio.listarResumo(sistema.listarPatrimoniosAprovados(), false);
            Tela.linhaFina();

            int id = entrada.lerInt(" Digite o ID para ver os detalhes (0 para voltar): ");
            if (id == 0) {
                voltar = true;
            } else {
                Patrimonio patrimonio = sistema.buscarAprovadoPorId(id);
                if (patrimonio == null) {
                    Tela.erro("Patrimônio não encontrado no catálogo.");
                    entrada.aguardarEnter();
                } else {
                    detalhes(patrimonio);
                }
            }
        }
    }

    private void detalhes(Patrimonio patrimonio) {
        boolean voltar = false;

        while (!voltar) {
            TelaPatrimonio.mostrarDetalhes(patrimonio);
            int opcao = entrada.lerOpcaoMenu("O que deseja fazer?", "Voltar à listagem",
                    "Criar relato",
                    "Registrar denúncia");

            switch (opcao) {
                case 1 -> criarRelato(patrimonio);
                case 2 -> registrarDenuncia(patrimonio);
                case 0 -> voltar = true;
            }
        }
    }

    private void criarRelato(Patrimonio patrimonio) {
        Tela.subtitulo("Novo relato");
        String titulo = entrada.lerTexto(" Título: ");
        String conteudo = entrada.lerTexto(" Conte o seu relato: ");

        try {
            morador.criarRelato(titulo, conteudo, patrimonio);
            Tela.sucesso("Relato publicado! Pontos adicionados à sua conta.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Tela.erro(e.getMessage());
        }
        entrada.aguardarEnter();
    }

    private void registrarDenuncia(Patrimonio patrimonio) {
        Tela.subtitulo("Nova denúncia");
        TipoDenuncia tipo = entrada.lerEnum(" Tipo da denúncia:", TipoDenuncia.values());
        String descricao = entrada.lerTexto(" Descreva o problema: ");

        try {
            morador.registrarDenuncia(tipo, descricao, patrimonio);
            Tela.sucesso("Denúncia enviada! Ela será publicada após análise de um administrador.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Tela.erro(e.getMessage());
        }
        entrada.aguardarEnter();
    }

    // ---------- Cadastro de patrimônio ----------

    private void cadastrarPatrimonio() {
        try {
            Patrimonio patrimonio = new FormularioPatrimonio(entrada).ler(morador);
            sistema.cadastrarPatrimonio(patrimonio);
            Tela.sucesso("Patrimônio enviado! Ele aparecerá no catálogo após a aprovação de um administrador.");
        } catch (IllegalArgumentException e) {
            Tela.erro(e.getMessage());
        }
        entrada.aguardarEnter();
    }

    // ---------- Meu perfil ----------

    private void perfil() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("MEU PERFIL");
            int opcao = entrada.lerOpcao0("O que deseja ver?",
                    "Meus patrimônios",
                    "Meus relatos",
                    "Minhas denúncias",
                    "Alterar senha",
                    "Meus dados");

            switch (opcao) {
                case 1 -> {
                    Tela.subtitulo("Meus patrimônios");
                    TelaPatrimonio.listarResumo(morador.getPatrimoniosCadastrados(), true);
                    entrada.aguardarEnter();
                }
                case 2 -> {
                    Tela.subtitulo("Meus relatos");
                    TelaPatrimonio.listarRelatos(morador.getRelatos(), true);
                    entrada.aguardarEnter();
                }
                case 3 -> {
                    Tela.subtitulo("Minhas denúncias");
                    TelaPatrimonio.listarDenuncias(morador.getDenuncias(), true);
                    entrada.aguardarEnter();
                }
                case 4 -> telaPerfil.alterarSenha(morador);
                case 5 -> telaPerfil.meusDados(morador);
                case 0 -> voltar = true;
            }
        }
    }
}