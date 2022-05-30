/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cli;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author nicol
 */
public class App {

    public static void main(String[] args) throws UnknownHostException {
        ConnectionAzure azure = new ConnectionAzure();
        Inserts inserts = new Inserts();

        try {
            inserts.insertMaquinas();
            Boolean contador = true;
            Looca looca = new Looca();
            Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
            JdbcTemplate con = new JdbcTemplate(azure.getDataSource());
            System.out.println("Conexão obtida com sucesso!");



            while (contador) {

                String espaco = "==========";
                List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();
                Double discoDisponivel = 0.0;

                for (Volume volume : volumes) {
                    discoDisponivel += volume.getDisponivel();
                }

                List<Maquinas> listaMaquinas = con.query("SELECT idMaquina FROM maquinas;",
                        new BeanPropertyRowMapper<>(Maquinas.class));

                Maquinas maquina = listaMaquinas.get(0);

                List<ComponentesMaquinas> idCpu = con.query("SELECT idComponenteMaquina from componentes_has_maquinas WHERE fkMaquina = " + maquina.getIdMaquina() + " AND fkComponente = '3';",
                        new BeanPropertyRowMapper<>(ComponentesMaquinas.class));

                List<ComponentesMaquinas> idMemoria = con.query("SELECT idComponenteMaquina from componentes_has_maquinas WHERE fkMaquina = " + maquina.getIdMaquina() + " AND fkComponente = '1';",
                        new BeanPropertyRowMapper<>(ComponentesMaquinas.class));

                List<ComponentesMaquinas> idDisco = con.query("SELECT idComponenteMaquina from componentes_has_maquinas WHERE fkMaquina = " + maquina.getIdMaquina() + " AND fkComponente = '2';",
                        new BeanPropertyRowMapper<>(ComponentesMaquinas.class));

                BigDecimal consumoRAM = new BigDecimal(looca.getMemoria().getEmUso().doubleValue() / 1073741824).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal percentualCPU = new BigDecimal(looca.getProcessador().getUso()).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal consumoDisco = new BigDecimal(discoDisponivel / 1e+9).setScale(0, RoundingMode.HALF_EVEN);
                Date data = new Date();
                SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dataFormatada = formatar.format(data);

                System.out.println(String.format("%s %.2f%% %s %.2f GB USADOS %s %.2f LIVRE",
                        espaco, looca.getProcessador().getUso(),
                        espaco, ram, espaco, consumoDisco));

                // Registros:
                String insertReg = "INSERT INTO registros (fkComponenteMaquina, fkComponente, fkMaquina, dataHora, totalUsado) VALUES (?, ?, ?, ?, ?)";

                //CPU
                con.update(insertReg,
                        idCpu.get(0).getIdComponenteMaquina(),
                        3,
                        maquina.getIdMaquina(),
                        dataFormatada,
                        percentualCPU
                );

                //RAM
                con.update(insertReg,
                        idMemoria.get(0).getIdComponenteMaquina(),
                        1,
                        maquina.getIdMaquina(),
                        dataFormatada,
                        consumoRAM
                );

                //DISCO
                con.update(insertReg,
                        idDisco.get(0).getIdComponenteMaquina(),
                        2,
                        maquina.getIdMaquina(),
                        dataFormatada,
                        consumoDisco
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
