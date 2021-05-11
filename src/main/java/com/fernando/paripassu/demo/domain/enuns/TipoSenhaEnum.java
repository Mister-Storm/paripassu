package com.fernando.paripassu.demo.domain.enuns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TipoSenhaEnum {

    NORMAL("N"),
    PREFERENCIAL("P");

    private String tipo;

    private static final Map<TipoSenhaEnum, String> tipoSenhaValor = new HashMap<>();

    static {
        for (TipoSenhaEnum tipoSenha : TipoSenhaEnum.values()) {
            tipoSenhaValor.put(tipoSenha, tipoSenha.getValor());
        }
    }

    TipoSenhaEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return tipo;
    }

}
