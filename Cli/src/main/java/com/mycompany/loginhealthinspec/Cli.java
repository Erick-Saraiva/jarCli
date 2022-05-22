package com.mycompany.loginhealthinspec;

import com.github.britooo.looca.api.core.Looca;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Cli {

    public static void main(String[] args) {
        Looca looca = new Looca();
        Log log = new Log();
        Integer processList = looca.getGrupoDeProcessos().getProcessos().size();

        /*
        // MAP DE PROCESSOS QUE SERÃO FECHADOS
        Map<Integer, String> blackList = new HashMap<Integer, String>();
        blackList.put(0, "notepad");
        blackList.put(1, "gedit");

        //Try que pega todos os processos que estão sendo executados
        try {
            for (int i = 0; i < processList; i++) {
                String fraseProcesso = "";

                System.out.println(looca.getGrupoDeProcessos().getProcessos().get(i).getNome() + "\n");
                fraseProcesso = looca.getGrupoDeProcessos().getProcessos().get(i).getNome();
                log.guardarLog("Processo: " + fraseProcesso + " em execução.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
        try {
            String espaco = "==========";
            Double ram = looca.getMemoria().getEmUso() / 1073741824.0;
            Double disco = looca.getGrupoDeDiscos().getTamanhoTotal() / 1073741824.0;
            Integer contador = 0;
            double tamanho = new File("C:\\").getTotalSpace() - new File("C:\\").getFreeSpace();

            System.out.println(String.format("%s CAPTURANDO DADOS DA MÁQUINA %s", espaco, espaco));
            System.out.println(String.format("%s DADOS CAPTURADOS DA MÁQUINA: %s", espaco, espaco));
            System.out.println(String.format("%s PROCESSADOR %s MEMÓRIA RAM %s DISCO %s",
                         espaco, espaco, espaco, espaco));
            while (contador < 50) {

                System.out.println(String.format("%s %.2f%% %s %.2f GB USADOS %s %.2f USADOS",
                        espaco, looca.getProcessador().getUso(), 
                        espaco, ram, espaco, tamanho / 1073741824.0));
            }

            /* System.out.println(String.format("%.2f GB usados", ram));
            System.out.println(String.format("%.2f usados", tamanho / 1073741824.0));
             */
        } catch (Exception e) {

            e.printStackTrace();

        }

        // while que verifica o uso de CPU e RAM(falta o disco)
        //While da blacklist
        /*
        Boolean verdadeiro = true;
        while (verdadeiro) {
            Integer processlist = looca.getGrupoDeProcessos().getProcessos().size();
            for (int i = 0; i < processlist; i++) {
                try {
                    if (blackList.containsValue(looca.getGrupoDeProcessos().getProcessos().get(i).getNome())) {
                        if (looca.getSistema().getSistemaOperacional().equals("Windows")) {
                            String processoFinalizado = looca.getGrupoDeProcessos().getProcessos().get(i).getNome();
                            String killWindows = "taskkill /F /T /PID " + looca.getGrupoDeProcessos().getProcessos().get(i).getPid();
                            Runtime.getRuntime().exec(killWindows);
                            log.guardarLog("Processo: " + processoFinalizado + " finalizado!");
                            looca.getGrupoDeProcessos().getProcessos().remove(i);
                        }
                        if (looca.getSistema().getSistemaOperacional().equals("Ubuntu")) {
                            String killUbuntu = "kill -SIGKILL " + looca.getGrupoDeProcessos().getProcessos().get(i).getPid();
                            Runtime.getRuntime().exec(killUbuntu);
                            looca.getGrupoDeProcessos().getProcessos().remove(i);
                        }
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
         */
    }

    @Override
    public String toString() {
        return "";
    }
    
}
