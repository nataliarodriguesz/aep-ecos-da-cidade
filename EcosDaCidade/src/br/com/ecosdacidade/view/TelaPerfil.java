package br.com.ecosdacidade.view;

import br.com.ecosdacidade.model.usuario.Morador;
import br.com.ecosdacidade.model.usuario.Usuario;

import java.time.format.DateTimeFormatter;

public class TelaPerfil {
    private final Entrada entrada;

    public TelaPerfil(Entrada entrada) {
        this.entrada = entrada;
    }

    public void meusDados(Usuario usuario) {
        Tela.subtitulo("Meus dados");
        System.out.println(" Nome: " + usuario.getNome());
        System.out.println(" E-mail: " + usuario.getEmail());
        System.out.println(" Cadastro em: "
                + usuario.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        if (usuario instanceof Morador morador) {
            System.out.println(" Pontuação: " + morador.getPontuacao() + " pontos");
            System.out.println(" Patrimônios cadastrados: " + morador.getPatrimoniosCadastrados().size());
        } else {
            System.out.println(" Tipo de conta: Administrador");
        }
        entrada.aguardarEnter();
    }

    public void alterarSenha(Usuario usuario) {
        Tela.subtitulo("Alterar senha");
        String atual = entrada.lerTexto(" Senha atual: ");
        String nova = entrada.lerTexto(" Nova senha (mínimo 6 caracteres): ");
        String confirmacao = entrada.lerTexto(" Confirme a nova senha: ");

        if (!nova.equals(confirmacao)) {
            Tela.erro("A confirmação não confere com a nova senha.");
        } else {
            try {
                usuario.alterarSenha(atual, nova);
                Tela.sucesso("Senha alterada com sucesso!");
            } catch (IllegalArgumentException e) {
                Tela.erro(e.getMessage());
            }
        }
        entrada.aguardarEnter();
    }
}