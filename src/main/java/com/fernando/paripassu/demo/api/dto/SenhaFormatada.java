package com.fernando.paripassu.demo.api.dto;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;

public class SenhaFormatada {

    private Integer senha;
    private TipoSenhaEnum tipoSenhaEnum;

    private SenhaFormatada(Integer senha, TipoSenhaEnum tipoSenhaEnum) {
        this.senha = senha;
        this.tipoSenhaEnum = tipoSenhaEnum;
    }

    public static SenhaFormatada of(Integer senha, TipoSenhaEnum tipoSenhaEnum) {
        return new SenhaFormatada(senha, tipoSenhaEnum);
    }

    public Boolean isSenhaPreferencial() {
        return tipoSenhaEnum.equals(TipoSenhaEnum.PREFERENCIAL);
    }

    @Override
    public String toString() {
        String senhaFormatada;
        if(senha < 10) {
            senhaFormatada =  tipoSenhaEnum.getValor()+"000"+senha;
        } else if(senha > 10 && senha < 100) {
            senhaFormatada = tipoSenhaEnum.getValor()+"00"+senha;
        } else if(senha > 100 && senha < 1000) {
            senhaFormatada = tipoSenhaEnum.getValor()+"0"+senha;
        } else {
            senhaFormatada = tipoSenhaEnum.getValor()+senha;
        }
        return senhaFormatada;
    }
}
