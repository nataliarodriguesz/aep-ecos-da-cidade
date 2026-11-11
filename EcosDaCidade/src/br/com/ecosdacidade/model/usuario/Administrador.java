package br.com.ecosdacidade.model.usuario;

import br.com.ecosdacidade.model.Relato;
import br.com.ecosdacidade.model.StatusAprovacao;
import br.com.ecosdacidade.model.StatusRelato;
import br.com.ecosdacidade.model.denuncia.Denuncia;
import br.com.ecosdacidade.model.denuncia.StatusDenuncia;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;

public class Administrador extends Usuario {
    private static final int PONTOS_PATRIMONIO_APROVADO = 30;
    private static final int PONTOS_DENUNCIA_APROVADA = 15;

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
