package br.com.ecosdacidade.view;

public class Tela {
    public static final String RESET = "\u001B[0m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String CIANO = "\u001B[36m";
    public static final String NEGRITO = "\u001B[1m";

    private static final int LARGURA = 50;

    private Tela() {
    }

    public static void limpar() {
        System.out.println("\n".repeat(2));
    }

    public static void linha() {
        System.out.println(CIANO + "═".repeat(LARGURA) + RESET);
    }

    public static void linhaFina() {
        System.out.println(CIANO + "─".repeat(LARGURA) + RESET);
    }

    public static void titulo(String texto) {
        System.out.println();
        linha();
        System.out.println(NEGRITO + centralizar(texto) + RESET);
        linha();
    }

    public static void subtitulo(String texto) {
        System.out.println();
        System.out.println(NEGRITO + CIANO + " " + texto + RESET);
        linhaFina();
    }

    public static void sucesso(String mensagem) {
        System.out.println(VERDE + " ✔ " + mensagem + RESET);
    }

    public static void erro(String mensagem) {
        System.out.println(VERMELHO + " ✖ " + mensagem + RESET);
    }

    public static void aviso(String mensagem) {
        System.out.println(AMARELO + " ! " + mensagem + RESET);
    }

    public static void abertura() {
        System.out.println();
        linha();
        System.out.println(NEGRITO + VERDE + centralizar("ECOS DA CIDADE") + RESET);
        System.out.println(AMARELO + centralizar("MEMÓRIA • HISTÓRIA • PATRIMÔNIO") + RESET);
        linha();
        System.out.println(centralizar("Preserve e conheça os lugares que contam"));
        System.out.println(centralizar("a história da nossa cidade."));
        linhaFina();
    }

    private static String centralizar(String texto) {
        int espacos = Math.max(0, (LARGURA - texto.length()) / 2);
        return " ".repeat(espacos) + texto;
    }
}