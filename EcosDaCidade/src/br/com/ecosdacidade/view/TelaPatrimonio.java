package br.com.ecosdacidade.view;

import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.relato.Relato;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaPatrimonio {
    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private TelaPatrimonio() {
    }

    // Serve para qualquer status: verde = ok, amarelo = pendente, vermelho = rejeitado/removido
    public static String colorirStatus(Enum<?> status) {
        String cor = switch (status.name()) {
            case "APROVADO", "PUBLICADO", "PUBLICADA" -> Tela.VERDE;
            case "PENDENTE" -> Tela.AMARELO;
            default -> Tela.VERMELHO;
        };
        return cor + status.name() + Tela.RESET;
    }

    public static void listarResumo(List<Patrimonio> patrimonios, boolean mostrarStatus) {
        if (patrimonios.isEmpty()) {
            Tela.aviso("Nenhum patrimônio para mostrar.");
            return;
        }
        for (Patrimonio p : patrimonios) {
            System.out.println(" " + Tela.AMARELO + "[" + p.getId() + "]" + Tela.RESET + " "
                    + Tela.NEGRITO + p.getNome() + Tela.RESET
                    + "  (" + p.getClass().getSimpleName() + ")");
            System.out.println("      " + p.getEndereco().getLogradouro() + ", " + p.getEndereco().getNumero()
                    + " - " + p.getEndereco().getBairro() + ", " + p.getEndereco().getCidade());
            if (mostrarStatus) {
                System.out.println("      Status: " + colorirStatus(p.getStatusAprovacao()));
            }
            System.out.println();
        }
    }

    public static void mostrarDetalhes(Patrimonio patrimonio) {
        System.out.println();
        System.out.println(patrimonio.gerarDetalhes());

        List<Relato> relatos = patrimonio.getRelatosPublicados();
        Tela.subtitulo("Relatos (" + relatos.size() + ")");
        listarRelatos(relatos, false);

        List<Denuncia> denuncias = patrimonio.getDenunciasPublicadas();
        Tela.subtitulo("Denúncias (" + denuncias.size() + ")");
        listarDenuncias(denuncias, false);
    }

    // modoPerfil = true: mostra o patrimônio e o status (usado em "Meus relatos")
    public static void listarRelatos(List<Relato> relatos, boolean modoPerfil) {
        if (relatos.isEmpty()) {
            Tela.aviso("Nenhum relato.");
            return;
        }
        for (Relato r : relatos) {
            System.out.println(" " + Tela.AMARELO + "[" + r.getId() + "]" + Tela.RESET + " "
                    + Tela.NEGRITO + r.getTitulo() + Tela.RESET);
            if (modoPerfil) {
                System.out.println("      Patrimônio: " + r.getPatrimonio().getNome());
                System.out.println("      Status: " + colorirStatus(r.getStatus()));
            } else {
                System.out.println("      Por " + r.getAutor().getNome());
            }
            System.out.println("      Em " + r.getDataPublicacao().format(DATA_HORA));
            System.out.println("      " + r.getConteudo());
            System.out.println();
        }
    }

    public static void listarDenuncias(List<Denuncia> denuncias, boolean modoPerfil) {
        if (denuncias.isEmpty()) {
            Tela.aviso("Nenhuma denúncia.");
            return;
        }
        for (Denuncia d : denuncias) {
            System.out.println(" " + Tela.AMARELO + "[" + d.getId() + "]" + Tela.RESET + " "
                    + Tela.NEGRITO + d.getTipo().name().replace('_', ' ') + Tela.RESET);
            if (modoPerfil) {
                System.out.println("      Patrimônio: " + d.getPatrimonio().getNome());
                System.out.println("      Status: " + colorirStatus(d.getStatus()));
            } else {
                System.out.println("      Por " + d.getMoradorResponsavel().getNome());
            }
            System.out.println("      Em " + d.getDataRegistro().format(DATA_HORA));
            System.out.println("      " + d.getDescricao());
            System.out.println();
        }
    }

    public static void mostrarDenuncia(Denuncia d) {
        Tela.subtitulo("Denúncia [" + d.getId() + "]");
        System.out.println(" Tipo: " + d.getTipo().name().replace('_', ' '));
        System.out.println(" Patrimônio: [" + d.getPatrimonio().getId() + "] " + d.getPatrimonio().getNome());
        System.out.println(" Autor: " + d.getMoradorResponsavel().getNome());
        System.out.println(" Em: " + d.getDataRegistro().format(DATA_HORA));
        System.out.println(" Status: " + colorirStatus(d.getStatus()));
        System.out.println(" Descrição: " + d.getDescricao());
    }
}