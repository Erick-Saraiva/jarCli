package com.mycompany.loginhealthinspec;

import com.github.britooo.looca.api.core.Looca;
import java.io.File;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.jdbc.core.JdbcTemplate;

public class Cli {

    public static void main(String[] args) {
        Looca looca = new Looca();
        Inserts inserts = new Inserts();
        Random gerador = new Random();
        ConnectionMySql connectionMySql = new ConnectionMySql();
        JdbcTemplate con = new JdbcTemplate(connectionMySql.getDataSource());

        try {
            String espaco = "==========";
            Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
            Boolean contador = true;
            double tamanho = new File("C:\\").getTotalSpace() - new File("C:\\").getFreeSpace();          

            System.out.println(String.format("%s CAPTURANDO DADOS DA MÁQUINA %s", espaco, espaco));
            System.out.println(String.format("%s DADOS CAPTURADOS DA MÁQUINA: %s", espaco, espaco));
            System.out.println(String.format("%s PROCESSADOR %s MEMÓRIA RAM %s DISCO %s",
                    espaco, espaco, espaco, espaco));

            while (contador) {

                System.out.println(String.format("%s %.2f%% %s %.2f GB USADOS %s %.2f USADOS",
                        espaco, looca.getProcessador().getUso(),
                        espaco, ram, espaco, tamanho / 1073741824.0));
                inserts.insertMaquinas();
                inserts.insertComponentes();
                inserts.insertRegistros();
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "";
    }

}
