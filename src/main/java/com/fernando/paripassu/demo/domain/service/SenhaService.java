package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.model.Senha;

import javax.inject.Inject;
import javax.inject.Named;
/*
* corrigir anotations e criar provider com implementacao simples em arquivo txt
* */
@Named
public class SenhaService {

    @Inject
    private Senha senhaNormal;
    @Inject
    private Senha senhaPreferencial;


    public SenhaService(Senha senhaNormal, Senha senhaPreferencial) {
        this.senhaNormal = senhaNormal;
        this.senhaPreferencial = senhaPreferencial;
    }
}
