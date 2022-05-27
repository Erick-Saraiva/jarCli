/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cli;

import com.github.britooo.looca.api.core.Looca;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author nicol
 */
public class App {

    public static void main(String[] args) throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        ConnectionAzure azure = new ConnectionAzure();
        JdbcTemplate con = new JdbcTemplate(azure.getDataSource());
        Scanner scan = new Scanner(System.in);
        Looca looca = new Looca();
        Inserts inserts = new Inserts();
        ResultSet resultSetEmail = null;
        String so = looca.getSistema().getSistemaOperacional();
        Integer bits = looca.getSistema().getArquitetura();

        try {
            Boolean contador = true;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
            Statement statement = connection.createStatement();
            System.out.println("Conexão obtida com sucesso!");          

                while (contador) {
                        inserts.msg();
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
