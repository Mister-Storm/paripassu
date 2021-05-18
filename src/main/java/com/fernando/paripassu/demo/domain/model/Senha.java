package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;

import java.util.Objects;

public class Senha<T> {

    private final T senha;
    private final TipoSenhaEnum tipoSenha;

    public Senha(T senha, TipoSenhaEnum tipoSenha) {
        this.senha = senha;
        this.tipoSenha = tipoSenha;
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
