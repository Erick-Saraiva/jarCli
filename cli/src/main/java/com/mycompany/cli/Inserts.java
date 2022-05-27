package com.mycompany.cli;

import com.github.britooo.looca.api.core.Looca;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    Integer bits = looca.getSistema().getArquitetura();
    ResultSet resultSetEmail = null;

    String tipoMaquina = "Computador";
    Random gerador = new Random();

    Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
    Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
    double tamanho = new File("C:\\").getTotalSpace() - new File("C:\\").getFreeSpace();
    double tamanhoTotal = new File("C:\\").getTotalSpace();

    public void msg() throws UnknownHostException {
        Boolean contador = true;
        String espaco = "==========";
        String so = looca.getSistema().getSistemaOperacional();
        Integer bits = looca.getSistema().getArquitetura();
        ConnectionAzure azure = new ConnectionAzure();
        JdbcTemplate con = new JdbcTemplate(azure.getDataSource());
        String hostName = InetAddress.getLocalHost().getHostName();
        System.out.println(String.format("%s CAPTURANDO DADOS DA MÁQUINA %s", espaco, espaco));
        System.out.println(String.format("%s DADOS CAPTURADOS DA MÁQUINA: %s", espaco, espaco));
        System.out.println(String.format("%s PROCESSADOR %s MEMÓRIA RAM %s DISCO %s",
                espaco, espaco, espaco, espaco));
        
        String insertComp = "INSERT INTO componentes (nomeComponente) VALUES (?)";
        

        while (contador) {

                                System.out.println(String.format("%s %.2f%% %s %.2f GB USADOS %s %.2f USADOS",
                            espaco, looca.getProcessador().getUso(),
                            espaco, ram, espaco, tamanho / 1073741824.0));

                    // Máquinas
                    String insertMaq = "INSERT INTO maquinas (tipoMaquina, fkTecnico, nomeMaquina, sistemaOperacional, arquitetura) VALUES (?, ?, ?, ?, ?)";

                    con.update(insertMaq,
                            "Computador",
                            gerador.nextInt(2) + 1,
                            hostName,
                            so,
                            bits
                    );  
                    
                    

                    // Componentes:
                    String insertCompMaq = "INSERT INTO componentes_has_maquinas (totalComponente, unidadeMedida, fkComponente, fkMaquina) VALUES (?, ?, ?, ?);";

                    //CPU
                    con.update(insertCompMaq,
                            looca.getProcessador().getFrequencia(),
                            "Ghz",
                            13,
                            gerador.nextInt(2) + 1
                    );
                    //RAM
                    con.update(insertCompMaq,
                            String.format("Memória de %.1f", 
                                    ram),
                            "Gb",
                            12,
                            gerador.nextInt(2) + 1
                    );
                    //DISCO
                    con.update(insertCompMaq,
                            String.format("Disco de %.1f",
                                    tamanhoTotal),
                            "Gb",
                            14,
                            gerador.nextInt(2) + 1
                    );

                    // Registros:
                    String insertReg = "INSERT INTO registros (dataHora, totalUsado, fkComponenteMaquina) VALUES (?, ?, ?)";

                    //CPU
                    con.update(insertReg,
                            LocalDateTime.now(),
                            looca.getProcessador().getUso(),
                            gerador.nextInt(2) + 1
                    );

                    //RAM
                    con.update(insertReg,
                            LocalDateTime.now(),
                            looca.getMemoria().getEmUso(),
                            gerador.nextInt(2) + 1
                    );

                    //DISCO
                    con.update(insertReg,
                            LocalDateTime.now(),
                            tamanho,
                            gerador.nextInt(2) + 1);

        }

    }
}
