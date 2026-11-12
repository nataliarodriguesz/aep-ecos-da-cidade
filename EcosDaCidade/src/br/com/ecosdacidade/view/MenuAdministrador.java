package br.com.ecosdacidade.view;

import br.com.ecosdacidade.model.relato.Relato;
import br.com.ecosdacidade.model.SistemaPatrimonio;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.usuario.Administrador;

import java.util.List;

public class MenuAdministrador {
    private final Entrada entrada;
    private final SistemaPatrimonio sistema;
    private final Administrador admin;
    private final TelaPerfil telaPerfil;

    public MenuAdministrador(Entrada entrada, SistemaPatrimonio sistema, Administrador admin) {
        this.entrada = entrada;
        this.sistema = sistema;
        this.admin = admin;
        this.telaPerfil = new TelaPerfil(entrada);
    }

    public void iniciar() {
        Tela.titulo("Olá, " + admin.getNome() + "!");
        boolean sair = false;

        while (!sair) {
            int opcao = entrada.lerOpcaoMenu("O que deseja fazer?", "Sair da conta",
                    "Início (catálogo de patrimônios)",
                    "Patrimônios pendentes (" + sistema.listarPatrimoniosPendentes().size() + ")",
                    "Denúncias pendentes (" + sistema.listarDenunciasPendentes().size() + ")",
                    "Meu perfil");

            switch (opcao) {
                case 1 -> catalogo();
                case 2 -> patrimoniosPendentes();
                case 3 -> denunciasPendentes();
                case 4 -> perfil();
                case 0 -> sair = true;
            }
        }
        Tela.aviso("Você saiu da conta.");
    }

    // ---------- Catálogo e gerenciamento ----------

    private void catalogo() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("CATÁLOGO DE PATRIMÔNIOS");
            TelaPatrimonio.listarResumo(sistema.listarPatrimoniosAprovados(), false);
            Tela.linhaFina();

            int id = entrada.lerInt(" Digite o ID para gerenciar (0 para voltar): ");
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

        // Se o patrimônio for removido, sai da tela (ele não está mais no catálogo)
        while (!voltar && patrimonio.estaAprovado()) {
            TelaPatrimonio.mostrarDetalhes(patrimonio);
            int opcao = entrada.lerOpcaoMenu("Gerenciar", "Voltar à listagem",
                    "Remover um relato",
                    "Remover uma denúncia",
                    "Remover este patrimônio");

            switch (opcao) {
                case 1 -> removerRelato(patrimonio);
                case 2 -> removerDenuncia(patrimonio);
                case 3 -> removerPatrimonio(patrimonio);
                case 0 -> voltar = true;
            }
        }
    }

    private void removerRelato(Patrimonio patrimonio) {
        List<Relato> relatos = patrimonio.getRelatosPublicados();
        if (relatos.isEmpty()) {
            Tela.aviso("Não há relatos publicados para remover.");
            entrada.aguardarEnter();
            return;
        }

        int id = entrada.lerInt(" ID do relato a remover (0 para cancelar): ");
        if (id == 0) {
            return;
        }

        Relato escolhido = null;
        for (Relato r : relatos) {
            if (r.getId() == id) {
                escolhido = r;
            }
        }

        if (escolhido == null) {
            Tela.erro("Relato não encontrado neste patrimônio.");
        } else if (entrada.confirmar(" Remover o relato \"" + escolhido.getTitulo() + "\"?")) {
            try {
                admin.removerRelato(escolhido);
                Tela.sucesso("Relato removido.");
            } catch (IllegalStateException e) {
                Tela.erro(e.getMessage());
            }
        }
        entrada.aguardarEnter();
    }

    private void removerDenuncia(Patrimonio patrimonio) {
        List<Denuncia> denuncias = patrimonio.getDenunciasPublicadas();
        if (denuncias.isEmpty()) {
            Tela.aviso("Não há denúncias publicadas para remover.");
            entrada.aguardarEnter();
            return;
        }

        int id = entrada.lerInt(" ID da denúncia a remover (0 para cancelar): ");
        if (id == 0) {
            return;
        }

        Denuncia escolhida = null;
        for (Denuncia d : denuncias) {
            if (d.getId() == id) {
                escolhida = d;
            }
        }

        if (escolhida == null) {
            Tela.erro("Denúncia não encontrada neste patrimônio.");
        } else if (entrada.confirmar(" Remover esta denúncia?")) {
            try {
                admin.removerDenuncia(escolhida);
                Tela.sucesso("Denúncia removida.");
            } catch (IllegalStateException e) {
                Tela.erro(e.getMessage());
            }
        }
        entrada.aguardarEnter();
    }

    private void removerPatrimonio(Patrimonio patrimonio) {
        Tela.aviso("Isso também remove todos os relatos e denúncias deste patrimônio.");
        if (entrada.confirmar(" Remover \"" + patrimonio.getNome() + "\"?")) {
            try {
                admin.removerPatrimonio(patrimonio);
                Tela.sucesso("Patrimônio removido.");
            } catch (IllegalStateException e) {
                Tela.erro(e.getMessage());
            }
            entrada.aguardarEnter();
        }
    }

    // ---------- Patrimônios pendentes ----------

    private void patrimoniosPendentes() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("PATRIMÔNIOS PENDENTES");
            TelaPatrimonio.listarResumo(sistema.listarPatrimoniosPendentes(), true);
            Tela.linhaFina();

            int id = entrada.lerInt(" Digite o ID para analisar (0 para voltar): ");
            if (id == 0) {
                voltar = true;
            } else {
                Patrimonio patrimonio = sistema.buscarPendentePorId(id);
                if (patrimonio == null) {
                    Tela.erro("Não há patrimônio pendente com esse ID.");
                    entrada.aguardarEnter();
                } else {
                    analisarPatrimonio(patrimonio);
                }
            }
        }
    }

    private void analisarPatrimonio(Patrimonio patrimonio) {
        System.out.println();
        System.out.println(patrimonio.gerarDetalhes());
        System.out.println(" Cadastrado por: " + patrimonio.getMoradorResponsavel().getNome());

        int opcao = entrada.lerOpcaoMenu("Decisão", "Voltar sem decidir",
                "Aprovar",
                "Rejeitar");

        try {
            switch (opcao) {
                case 1 -> {
                    admin.aprovarPatrimonio(patrimonio);
                    Tela.sucesso("Patrimônio aprovado! O morador recebeu os pontos.");
                    entrada.aguardarEnter();
                }
                case 2 -> {
                    admin.rejeitarPatrimonio(patrimonio);
                    Tela.sucesso("Patrimônio rejeitado.");
                    entrada.aguardarEnter();
                }
            }
        } catch (IllegalStateException e) {
            Tela.erro(e.getMessage());
            entrada.aguardarEnter();
        }
    }

    // ---------- Denúncias pendentes ----------

    private void denunciasPendentes() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("DENÚNCIAS PENDENTES");
            TelaPatrimonio.listarDenuncias(sistema.listarDenunciasPendentes(), true);
            Tela.linhaFina();

            int id = entrada.lerInt(" Digite o ID para analisar (0 para voltar): ");
            if (id == 0) {
                voltar = true;
            } else {
                Denuncia denuncia = sistema.buscarDenunciaPendentePorId(id);
                if (denuncia == null) {
                    Tela.erro("Não há denúncia pendente com esse ID.");
                    entrada.aguardarEnter();
                } else {
                    analisarDenuncia(denuncia);
                }
            }
        }
    }

    private void analisarDenuncia(Denuncia denuncia) {
        TelaPatrimonio.mostrarDenuncia(denuncia);

        int opcao = entrada.lerOpcaoMenu("Decisão", "Voltar sem decidir",
                "Publicar (aprovar)",
                "Rejeitar");

        try {
            switch (opcao) {
                case 1 -> {
                    admin.aprovarDenuncia(denuncia);
                    Tela.sucesso("Denúncia publicada! O morador recebeu os pontos.");
                    entrada.aguardarEnter();
                }
                case 2 -> {
                    admin.rejeitarDenuncia(denuncia);
                    Tela.sucesso("Denúncia rejeitada.");
                    entrada.aguardarEnter();
                }
            }
        } catch (IllegalStateException e) {
            Tela.erro(e.getMessage());
            entrada.aguardarEnter();
        }
    }

    // ---------- Meu perfil ----------

    private void perfil() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("MEU PERFIL");
            int opcao = entrada.lerOpcao0("O que deseja ver?",
                    "Meus dados",
                    "Alterar senha");

            switch (opcao) {
                case 1 -> telaPerfil.meusDados(admin);
                case 2 -> telaPerfil.alterarSenha(admin);
                case 0 -> voltar = true;
            }
        }
    }
}