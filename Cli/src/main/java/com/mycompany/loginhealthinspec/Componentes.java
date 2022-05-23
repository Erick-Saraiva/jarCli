package com.mycompany.loginhealthinspec;

public class Componentes {
    
    private Integer idComponente;
    private String nomeComponente;
    private String tipoComponente;
    private String descricaoComponente;
    private Integer fkMaquina;

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getDescricaoComponente() {
        return descricaoComponente;
    }

    public void setDescricaoComponente(String descricaoComponente) {
        this.descricaoComponente = descricaoComponente;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    @Override
    public String toString() {
        return "Componentes{" + "idComponente=" + 
                idComponente + ", nomeComponente=" + 
                nomeComponente + ", tipoComponente=" + 
                tipoComponente + ", descricaoComponente=" + 
                descricaoComponente + ", fkMaquina=" + fkMaquina + '}';
    }

    
    
    
}
