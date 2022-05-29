package com.mycompany.cli;

import com.github.britooo.looca.api.core.Looca;
import java.awt.Color;
import java.io.File;
import java.net.InetAddress;
import java.sql.DriverManager;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Inserts {
        
    public void insertMaquinas() {
    
    
    try {
            ConnectionAzure azure = new ConnectionAzure();
            Looca looca = new Looca();
            Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
            Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
            String so = looca.getSistema().getSistemaOperacional();
            Integer bits = looca.getSistema().getArquitetura();
            String hostName = InetAddress.getLocalHost().getHostName();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            java.sql.Connection connection = DriverManager.getConnection(azure.connectionUrl);
            JdbcTemplate con = new JdbcTemplate(azure.getDataSource());
            System.out.println("Conexão obtida com sucesso!");

            List<Funcionario> listaLoginFuncionario = con.query(
                    "SELECT idFuncionario, email, fkHospital, senha FROM funcionario;",
                    new BeanPropertyRowMapper<>(Funcionario.class));

            if (listaLoginFuncionario.isEmpty()) {
                System.out.println("Erro");
            } else {

                Funcionario funcionario = listaLoginFuncionario.get(0);

                List<Maquinas> listaMaquinas = con.query("SELECT * FROM maquinas WHERE fkHospital = " + funcionario.getFkHospital() + ";",
                        new BeanPropertyRowMapper<>(Maquinas.class));

                //System.out.println(listaMaquinas.get(0).toString());
                if (listaMaquinas.isEmpty()) {

                    String insertMaquinas = "INSERT INTO maquinas (tipoMaquina, nomeMaquina, sistemaOperacional, arquitetura, fkHospital) VALUES (?, ?, ?, ?, ?);";
                    con.update(insertMaquinas,
                            "Computador",
                            hostName,
                            so,
                            bits,
                            funcionario.getFkHospital()
                    );
                    listaMaquinas = con.query("SELECT * FROM maquinas WHERE fkHospital = " + funcionario.getFkHospital() + ";",
                        new BeanPropertyRowMapper<>(Maquinas.class));
                    
                    Maquinas maquinas = listaMaquinas.get(0);
                    
                    String insertCompMaq = "INSERT INTO componentes_has_maquinas (fkComponente, fkMaquina, totalComponente, unidadeMedida) VALUES (?, ?, ?, ?);";

                    //CPU
                    con.update(insertCompMaq,
                            3,
                            maquinas.getIdMaquina(),
                            String.format("%.1f",looca.getProcessador().getFrequencia().doubleValue()),
                            "Ghz"
                    );

                    //RAM
                    con.update(insertCompMaq,
                            1,
                            maquinas.getIdMaquina(),
                            String.format("%.1f Gb",
                                    ram),
                            "Gb"
                    );

                    //DISCO
                    con.update(insertCompMaq,
                            2,
                            maquinas.getIdMaquina(),
                            String.format("%.1f Gb",
                                    disco),
                            "Gb"
                    );


                } else {
                    System.out.println("Máquina já inserida!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
