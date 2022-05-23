package com.mycompany.loginhealthinspec;

import com.github.britooo.looca.api.core.Looca;
import java.io.File;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;

public class Cli {

    public static void main(String[] args) {
        Looca looca = new Looca();
        ConnectionMySql connectionMySql = new ConnectionMySql();
        JdbcTemplate con = new JdbcTemplate(connectionMySql.getDataSource());
        
        
        try {
            Integer numero = 1;
            String espaco = "==========";
            String tipoMaquina = "Computador-Recepção";
            String apelidoMaquina = String.format("Máquina-%d", numero);
            
            
            Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
            Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
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
                
                con.update("INSERT INTO maquinas VALUES (null, ?, ?, ?, ?, ?, 64)",
                        null, tipoMaquina, 1, apelidoMaquina, 
                        looca.getSistema().getSistemaOperacional(), 
                        LocalDateTime.now(), looca.getSistema().getArquitetura());
                
                con.update("INSERT INTO componentes VALUES (null, ?, ?, ?, ?)",
                        null, "Kingston", "RAM", "Memória Ram 8Gb 2666Mhz", 1);
                
                con.update("INSERT INTO registros VALUES (?, ?, ?, ?)",
                        null, LocalDateTime.now(), ram, 1);
                
                numero++;
            }

            /* System.out.println(String.format("%.2f GB usados", ram));
            System.out.println(String.format("%.2f usados", tamanho / 1073741824.0));
             */
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Override
    public String toString() {
        return "";
    }
    
}
