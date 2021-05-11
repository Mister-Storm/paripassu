package com.fernando.paripassu.demo.domain.enuns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TipoUsuarioEnum {

    GERENTE("gerente"),
    CLIENTE("cliente");

    private String tipo;

    private static final Map<String, TipoUsuarioEnum> tipoUsuarioValor = new HashMap<>();
    
    static {
        for (TipoUsuarioEnum tipoUsuario : TipoUsuarioEnum.values()) {
            tipoUsuarioValor.put(tipoUsuario.getValor(), tipoUsuario);
        }
    }

    private String getValor() {
        return tipo;
    }

    TipoUsuarioEnum(String tipo) {
        this.tipo = tipo;
    }

    public static Optional<TipoUsuarioEnum> getTipoUsuarioPorValor(String tipoUsuario) {
        return tipoUsuarioValor.get(tipoUsuario) == null ? Optional.empty() :
                Optional.of(tipoUsuarioValor.get(tipoUsuario));
    }


}
