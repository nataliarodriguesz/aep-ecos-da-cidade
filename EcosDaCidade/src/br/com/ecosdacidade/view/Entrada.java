package br.com.ecosdacidade.view;

import java.util.Scanner;

public class Entrada {
    private final Scanner scanner = new Scanner(System.in);

    public String lerTexto(String pergunta) {
        System.out.print(pergunta);
        return scanner.nextLine().trim();
    }

    public int lerInt(String pergunta) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(pergunta));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public int lerIntEntre(String pergunta, int min, int max) {
        while (true) {
            int valor = lerInt(pergunta);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.printf("O número deve estar entre %d e %d.%n", min, max);
        }
    }

    public double lerDouble(String pergunta) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(pergunta).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public int lerOpcao(String titulo, String... opcoes) {
        Tela.subtitulo(titulo);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(" " + Tela.AMARELO + (i + 1) + Tela.RESET + " - " + opcoes[i]);
        }
        return lerIntEntre(" Opção: ", 1, opcoes.length);
    }

    public int lerOpcao0(String titulo, String... opcoes) {
        return lerOpcaoMenu(titulo, "Voltar / Sair", opcoes);
    }

    public int lerOpcaoMenu(String titulo, String textoZero, String... opcoes) {
        Tela.subtitulo(titulo);
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(" " + Tela.AMARELO + (i + 1) + Tela.RESET + " - " + opcoes[i]);
        }
        System.out.println(" " + Tela.AMARELO + "0" + Tela.RESET + " - " + textoZero);
        return lerIntEntre(" Opção: ", 0, opcoes.length);
    }

    // Monta o menu a partir de qualquer enum. Adicionou valor no enum? O menu atualiza sozinho.
    public <E extends Enum<E>> E lerEnum(String titulo, E[] valores) {
        String[] nomes = new String[valores.length];
        for (int i = 0; i < valores.length; i++) {
            nomes[i] = valores[i].name();
        }
        return valores[lerOpcao(titulo, nomes) - 1];
    }
    public void aguardarEnter() {
        lerTexto("\n Pressione ENTER para continuar...");
    }

    public boolean confirmar(String pergunta) {
        String resposta = lerTexto(pergunta + " (s/n): ").toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }
}