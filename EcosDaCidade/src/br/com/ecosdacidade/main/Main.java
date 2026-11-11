package br.com.ecosdacidade.main;

import br.com.ecosdacidade.model.SistemaPatrimonio;
import br.com.ecosdacidade.model.usuario.Morador;

public class Main {
    public static void main(String[] args) {
        SistemaPatrimonio sistema = new SistemaPatrimonio();

        Morador morador = new Morador();

        morador.cadastrarPatrimonio(sistema);
    }
}
