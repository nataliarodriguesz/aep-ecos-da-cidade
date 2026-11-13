package br.com.ecosdacidade.model.usuario;

import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.relato.Relato;

public class Administrador extends Usuario {
    private static final int PONTOS_PATRIMONIO_APROVADO = 30;
    private static final int PONTOS_DENUNCIA_APROVADA = 15;

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public void aprovarPatrimonio(Patrimonio p) {
        p.aprovar();
        p.getMoradorResponsavel().adicionarPontos(PONTOS_PATRIMONIO_APROVADO);
    }

    public void aprovarDenuncia(Denuncia d) {
        d.aprovar();
        d.getMoradorResponsavel().adicionarPontos(PONTOS_DENUNCIA_APROVADA);
    }

    public void rejeitarPatrimonio(Patrimonio p) { p.rejeitar(); }
    public void rejeitarDenuncia(Denuncia d) { d.rejeitar(); }


    public void removerPatrimonio(Patrimonio p) { p.remover(); }
    public void removerDenuncia(Denuncia d) { d.remover(); }
    public void removerRelato(Relato r) { r.remover(); }
}
