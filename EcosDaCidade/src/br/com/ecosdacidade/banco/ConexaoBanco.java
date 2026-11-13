package br.com.ecosdacidade.banco;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    private static final String ARQUIVO_CONFIG = "config.properties";

    public static Connection conectar() throws SQLException {
        Properties config = new Properties();

        try (FileInputStream entrada = new FileInputStream(ARQUIVO_CONFIG)) {
            config.load(entrada);
        } catch (IOException e) {
            throw new SQLException("Não foi possível ler o arquivo " + ARQUIVO_CONFIG
                    + ". Ele está na raiz do projeto?", e);
        }

        String url = config.getProperty("db.url").trim();
        String usuario = config.getProperty("db.usuario").trim();
        String senha = config.getProperty("db.senha").trim();

        return DriverManager.getConnection(url, usuario, senha);
    }
}