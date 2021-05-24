package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.enuns.TipoUsuarioEnum;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;

import java.util.Optional;

public class Usuario implements IUsuario {

    private TipoUsuarioEnum tipoUsuario;
    private String nome;

    private Usuario (String nome, TipoUsuarioEnum tipoUsuario) {
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public Boolean isGerente() {
        return tipoUsuario.equals(TipoUsuarioEnum.GERENTE);
    }

    public static IUsuario newInstance(String nome, String tipoUsuario) throws TipoUsuarioEnumException {

        Optional<TipoUsuarioEnum> tipoUsuarioEnum = TipoUsuarioEnum.getTipoUsuarioPorValor(tipoUsuario.toLowerCase());
        if(tipoUsuarioEnum.isEmpty()) {
            throw new TipoUsuarioEnumException(tipoUsuario);
        }

        return new Usuario(nome, tipoUsuarioEnum.get());
    }
}
