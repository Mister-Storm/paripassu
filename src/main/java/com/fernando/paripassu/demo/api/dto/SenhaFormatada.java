package com.fernando.paripassu.demo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.Senha;

public class SenhaFormatada {

    private Integer senha;
    private TipoSenhaEnum tipoSenhaEnum;
    private String senhaFormatada;

    private SenhaFormatada(Integer senha, TipoSenhaEnum tipoSenhaEnum) {
        this.senha = senha;
        this.tipoSenhaEnum = tipoSenhaEnum;
        senhaFormatada = formataSenha();
    }

    public static SenhaFormatada of(Senha<Integer> senha) {
        return new SenhaFormatada(senha.getSenha(), senha.isSenhaPreferencial() ? TipoSenhaEnum.PREFERENCIAL :
                        TipoSenhaEnum.NORMAL);
    }

    public String getSenhaFormatada() {
        return senhaFormatada;
    }

    @JsonIgnore
    public Boolean isSenhaPreferencial() {
        return tipoSenhaEnum.equals(TipoSenhaEnum.PREFERENCIAL);
    }

    public String formataSenha() {

        if(senha < 10) {
            senhaFormatada =  tipoSenhaEnum.getValor()+"000"+senha;
        } else if(senha >= 10 && senha < 100) {
            senhaFormatada = tipoSenhaEnum.getValor()+"00"+senha;
        } else if(senha >= 100 && senha < 1000) {
            senhaFormatada = tipoSenhaEnum.getValor()+"0"+senha;
        } else {
            senhaFormatada = tipoSenhaEnum.getValor()+senha;
        }
        return senhaFormatada;
    }
}
