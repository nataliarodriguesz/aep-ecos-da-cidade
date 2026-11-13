package br.com.ecosdacidade.banco;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {

    public static void main(String[] args) {
        try (Connection conexao = ConexaoBanco.conectar();
             Statement comando = conexao.createStatement();
             ResultSet resultado = comando.executeQuery("SELECT COUNT(*) FROM usuario")) {

            System.out.println("Conectou com sucesso!");
            if (resultado.next()) {
                System.out.println("Usuários cadastrados: " + resultado.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Falhou: " + e.getMessage());
        }
    }
}