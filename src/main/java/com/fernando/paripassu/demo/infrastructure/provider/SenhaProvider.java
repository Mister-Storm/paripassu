package com.fernando.paripassu.demo.infrastructure.provider;

import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;

public class SenhaProvider implements SenhaRepository {
    @Override
    public Senha salvarUltimaGerada(Senha senha) {
        return null;
    }

    @Override
    public void salvarUltimaChamada(Senha senha) {

    }

    @Override
    public void reiniciarSenhas(IUsuario usuario) {

    }
}
