package com.fernando.paripassu.demo.domain.enuns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TipoSenhaEnum {

    NORMAL("N"),
    PREFERENCIAL("P");

    private String tipo;

    private static final Map<String, TipoSenhaEnum> tipoSenhaValor = new HashMap<>();

    static {
        for (TipoSenhaEnum tipoSenha : TipoSenhaEnum.values()) {
            tipoSenhaValor.put(tipoSenha.getValor(), tipoSenha);
        }
    }

    TipoSenhaEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return tipo;
    }

    public static Optional<TipoSenhaEnum> getTipoSenhaPorValor(String tipoSenha) {
        String tipoSenhaUC = tipoSenha.toUpperCase();
        return tipoSenhaValor.get(tipoSenhaUC) == null ? Optional.empty() :
                Optional.of(tipoSenhaValor.get(tipoSenhaUC));
    }

}
