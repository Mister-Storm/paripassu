package com.fernando.paripassu.demo.domain.repository;

import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;

import javax.inject.Named;

@Named
public interface SenhaRepository {

    Senha salvarUltimaGerada(Senha senha);

    void salvarUltimaChamada(Senha senha);

    void reiniciarSenhas(IUsuario usuario);

    Senha recuperarUltimaSenhaNormalChamada();

    Integer recuperarTamanhoSenhaNormal();
}
