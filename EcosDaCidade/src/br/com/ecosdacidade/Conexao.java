
package br.com.ecosdacidade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
        "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCPS)(HOST=db.freesql.com)(PORT=2484))(CONNECT_DATA=(SERVICE_NAME=26ai_un3c1)))";

    public static Connection conectar() throws SQLException {

        String usuario = System.getenv("DB_USER");
        String senha = System.getenv("DB_PASSWORD");

        if (usuario == null || senha == null) {
            throw new IllegalStateException(
                "Configure as variaveis DB_USER e DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(URL, usuario, senha);
    }
}
