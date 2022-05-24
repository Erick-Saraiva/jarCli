package com.mycompany.loginhealthinspec;

import com.github.britooo.looca.api.core.Looca;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.jdbc.core.JdbcTemplate;

public class Inserts {
    ConnectionAzure azure = new ConnectionAzure();
    Looca looca = new Looca();
    Long tempoDeUso = looca.getSistema().getTempoDeAtividade();
    Double horasLigado1 = tempoDeUso / 60.0;
    Double horasLigado2 = horasLigado1 / 60.0;
    String formatado = String.format("%.1f", horasLigado2);
    String so = looca.getSistema().getSistemaOperacional();
    ResultSet resultSetEmail = null;
    Integer bits = looca.getSistema().getArquitetura();

    String tipoMaquina = "Computador";
    Random gerador = new Random();
    ConnectionMySql connectionMySql = new ConnectionMySql();
    JdbcTemplate con = new JdbcTemplate(connectionMySql.getDataSource());

    Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
    Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
    double tamanho = new File("C:\\").getTotalSpace() - new File("C:\\").getFreeSpace();
    double tamanhoTotal = new File("C:\\").getTotalSpace();

    public void insertMaquinas() throws UnknownHostException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        /*
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
        Statement statement = connection.createStatement();
        System.out.println("Conexão obtida com sucesso!");
        */
        String hostName = InetAddress.getLocalHost().getHostName();
        con.update("INSERT INTO maquinas VALUES (?, ?, ?, ?, ?, ?)",
                null, tipoMaquina, hostName, so, bits, 1);
        /*
        statement.executeUpdate(String.format("INSERT INTO maquinas VALUES (%s, %d, %s, %s, %d)",
                tipoMaquina, 1,  hostName, so, bits));
        */
    }

    public void insertComponentes() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
        Statement statement = connection.createStatement();
        System.out.println("Conexão obtida com sucesso!");
        */
        // Processador
        con.update("INSERT INTO componentes VALUES (?, ?, ?, ?);",
                null, looca.getProcessador().getNome(),
                looca.getProcessador().getFabricante() + " "
                + looca.getProcessador().getMicroarquitetura(),
                gerador.nextInt(4) + 1);
        /*
        statement.executeUpdate(String.format("INSERT INTO componentes VALUES (%s, %s, %s, %d);",
                looca.getProcessador().getNome(), 
                "CPU", looca.getProcessador().getMicroarquitetura(),
                gerador.nextInt(4) + 1));
        */  
        // Memória Ram
        con.update("INSERT INTO componentes VALUES (?, ?, ?, ?);",
                null, "RAM", looca.getMemoria().toString(),
                gerador.nextInt(4) + 1);
        /*
        statement.executeUpdate(String.format("INSERT INTO componentes VALUES (%s, %s, %s, %d);",
                "RAM", 
                "8GB", looca.getMemoria().toString(),
                gerador.nextInt(4) + 1));
        */

        // Disco
        con.update("INSERT INTO componentes VALUES (?, ?, ?, ?);",
                null, "Disco", tamanhoTotal,
                gerador.nextInt(4) + 1);
        /*
        statement.executeUpdate(String.format("INSERT INTO componentes VALUES (%s, %s, %s, %d);",
                "Disco", looca.getGrupoDeDiscos().toString(), tamanhoTotal, 
                gerador.nextInt(4) + 1));
        */
    }

    public void insertRegistros() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        /*
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
        Statement statement = connection.createStatement();
        System.out.println("Conexão obtida com sucesso!");
        */
        
        // Processador
        con.update("INSERT INTO registros VALUES (?, ?, ?, ?)",
                null, LocalDateTime.now(), looca.getProcessador().getUso(),
                gerador.nextInt(2) + 1);
        
        /*
        statement.executeUpdate(String.format("INSERT INTO registros VALUES (%s, %.2f, %d);",
                LocalDateTime.now(), looca.getProcessador().getUso(), 
                gerador.nextInt(2) + 1));
        */
        
        // Memória
        con.update("INSERT INTO registros VALUES (?, ?, ?, ?)",
                null, LocalDateTime.now(), ram,
                gerador.nextInt(2) + 1);
        
        /*
        statement.executeUpdate(String.format("INSERT INTO registros VALUES (%s, %.2f, %d);",
                LocalDateTime.now(), ram, 
                gerador.nextInt(2) + 1));
        */
        
        // Disco
        con.update("INSERT INTO registros VALUES (?, ?, ?, ?)",
                null, LocalDateTime.now(), tamanho, gerador.nextInt(2) + 1);
        
        /* statement.executeUpdate(String.format("INSERT INTO registros VALUES (%s, %.2f, %d);",
                LocalDateTime.now(), tamanho, 
                gerador.nextInt(2) + 1));
        */
    }

}
