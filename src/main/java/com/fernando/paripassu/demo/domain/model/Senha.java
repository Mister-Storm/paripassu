package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class Senha<T> implements Serializable {

    private final T senha;
    private final TipoSenhaEnum tipoSenha;

    private Senha(T senha, TipoSenhaEnum tipoSenha) {
        this.senha = senha;
        this.tipoSenha = tipoSenha;
    }

    public static <T> Senha<T> newInstance(T senha, String tipoSenha) throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Optional<TipoSenhaEnum> tipoUsuarioEnum = TipoSenhaEnum.getTipoSenhaPorValor(tipoSenha.toUpperCase());
        if(tipoUsuarioEnum.isEmpty()) {
            throw new TipoSenhaEnumException(tipoSenha);
        }

        return new Senha(senha, tipoUsuarioEnum.get());
    }

    public T getSenha() {
        return senha;
    }

    public Boolean isSenhaPreferencial() {
        return tipoSenha.equals(TipoSenhaEnum.PREFERENCIAL);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Senha)) return false;
        Senha<?> senha1 = (Senha<?>) o;
        return Objects.equals(getSenha(), senha1.getSenha()) &&
                tipoSenha == senha1.tipoSenha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSenha(), tipoSenha);
    }
}
