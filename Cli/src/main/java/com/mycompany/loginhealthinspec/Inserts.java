package com.mycompany.loginhealthinspec;

import com.github.britooo.looca.api.core.Looca;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import org.springframework.jdbc.core.JdbcTemplate;

public class Inserts {

    Random gerador = new Random();
    Looca looca = new Looca();
    ConnectionMySql connectionMySql = new ConnectionMySql();
    JdbcTemplate con = new JdbcTemplate(connectionMySql.getDataSource());

    public void insertMaquinas() throws UnknownHostException {

        con.update("INSERT INTO maquinas VALUES (null, null, null, ?, ?, ?, 64)",
                null, null, InetAddress.getLocalHost().getHostName(),
                looca.getSistema().getSistemaOperacional(),
                looca.getSistema().getTempoDeAtividade(),
                looca.getSistema().getArquitetura(), gerador.nextInt(5) + 1);
    }

    public void insertComponentes() {

        // Processador
        con.update("INSERT INTO componenetes VALUES (null, ?, ?, ?)",
                null, looca.getProcessador().getNome(),
                looca.getProcessador().getNome() + " "
                + looca.getProcessador().getNumeroCpusFisicas(),
                gerador.nextInt(4) + 1);

        // Memória Ram
        con.update("INSERT INTO componenetes VALUES (null, ?, ?, ?)",
                null, looca.getMemoria().toString(), looca.getMemoria().getTotal(),
                gerador.nextInt(4) + 1);

        // Disco
        con.update("INSERT INTO componenetes VALUES (null, ?, ?, ?)",
                null, "Disco", looca.getGrupoDeDiscos().getTamanhoTotal(),
                gerador.nextInt(4) + 1);

        
    }

    /*
    Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
    Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
    double tamanho = new File("C:\\").getTotalSpace() - new File("C:\\").getFreeSpace();
     */
}
