package com.fernando.paripassu.demo.domain.repository;

import com.fernando.paripassu.demo.domain.model.Senha;

import javax.inject.Named;

@Named
public interface SenhaRepository {

    Senha salvar(Senha senha);

}
