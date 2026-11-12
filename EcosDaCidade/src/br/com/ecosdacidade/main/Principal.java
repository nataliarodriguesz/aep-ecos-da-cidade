package br.com.ecosdacidade.main;

import br.com.ecosdacidade.model.Endereco;
import br.com.ecosdacidade.model.EstadoConservacao;
import br.com.ecosdacidade.model.SistemaPatrimonio;
import br.com.ecosdacidade.model.SistemaUsuarios;
import br.com.ecosdacidade.model.patrimonio.Patrimonio;
import br.com.ecosdacidade.model.patrimonio.PracaHistorica;
import br.com.ecosdacidade.model.usuario.Administrador;
import br.com.ecosdacidade.model.usuario.Morador;
import br.com.ecosdacidade.view.Entrada;
import br.com.ecosdacidade.view.MenuAdministrador;
import br.com.ecosdacidade.view.MenuMorador;
import br.com.ecosdacidade.view.Tela;

public class Principal {
    private final Entrada entrada = new Entrada();
    private final SistemaUsuarios usuarios = new SistemaUsuarios();
    private final SistemaPatrimonio patrimonios = new SistemaPatrimonio();

    public void iniciar() {
        dadosDeTeste();
        boolean rodando = true;

        while (rodando) {
            Tela.abertura();
            int opcao = entrada.lerOpcao0("Menu inicial",
                    "Entrar como Morador",
                    "Entrar como Administrador");

            switch (opcao) {
                case 1 -> acessoMorador();
                case 2 -> acessoAdministrador();
                case 0 -> {
                    Tela.linhaFina();
                    System.out.println(" Obrigado por usar o Ecos da Cidade. Até logo!");
                    rodando = false;
                }
            }
        }
    }

    private void acessoMorador() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("ACESSO DO MORADOR");
            int opcao = entrada.lerOpcao0("O que deseja fazer?",
                    "Fazer login",
                    "Criar cadastro");

            switch (opcao) {
                case 1 -> {
                    Morador morador = loginMorador();
                    if (morador != null) {
                        new MenuMorador(entrada, patrimonios, morador).iniciar();
                        voltar = true;
                    }
                }
                case 2 -> cadastrarMorador();
                case 0 -> voltar = true;
            }
        }
    }

    private void acessoAdministrador() {
        boolean voltar = false;

        while (!voltar) {
            Tela.titulo("ACESSO DO ADMINISTRADOR");
            int opcao = entrada.lerOpcao0("O que deseja fazer?", "Fazer login");

            switch (opcao) {
                case 1 -> {
                    Administrador admin = loginAdministrador();
                    if (admin != null) {
                        new MenuAdministrador(entrada, patrimonios, admin).iniciar();
                        voltar = true;
                    }
                }
                case 0 -> voltar = true;
            }
        }
    }

    private Morador loginMorador() {
        Tela.subtitulo("Login do Morador");
        String email = entrada.lerTexto(" E-mail: ");
        String senha = entrada.lerTexto(" Senha: ");

        try {
            return usuarios.autenticarMorador(email, senha);
        } catch (IllegalArgumentException e) {
            Tela.erro(e.getMessage());
            entrada.aguardarEnter();
            return null;
        }
    }

    private Administrador loginAdministrador() {
        Tela.subtitulo("Login do Administrador");
        String email = entrada.lerTexto(" E-mail: ");
        String senha = entrada.lerTexto(" Senha: ");

        try {
            return usuarios.autenticarAdministrador(email, senha);
        } catch (IllegalArgumentException e) {
            Tela.erro(e.getMessage());
            entrada.aguardarEnter();
            return null;
        }
    }

    private void cadastrarMorador() {
        Tela.subtitulo("Criar cadastro");
        String nome = entrada.lerTexto(" Nome: ");
        String email = entrada.lerTexto(" E-mail: ");
        String senha = entrada.lerTexto(" Senha (mínimo 6 caracteres): ");

        try {
            usuarios.cadastrarMorador(nome, email, senha);
            Tela.sucesso("Cadastro realizado! Agora faça o login.");
        } catch (IllegalArgumentException e) {
            Tela.erro(e.getMessage());
        }
        entrada.aguardarEnter();
    }

    // TEMPORÁRIO (teste da Etapa 3): apague na Etapa 4
    private void dadosDeTeste() {
        Morador maria = usuarios.cadastrarMorador("Maria Teste", "maria@x.com", "123456");
        Patrimonio praca = new PracaHistorica(maria, "Praça da Matriz", "Praça central da cidade",
                EstadoConservacao.BOM,
                new Endereco("Av. Principal", 100, "Centro", "Cidade Exemplo", "PR"),
                5000);
        patrimonios.cadastrarPatrimonio(praca);
        praca.aprovar();
    }
}