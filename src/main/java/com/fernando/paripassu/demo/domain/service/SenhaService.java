package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import com.fernando.paripassu.demo.dto.SenhaFormatada;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SenhaService {

    @Inject
    private Senha senhaNormal;
    @Inject
    private Senha senhaPreferencial;

    @Inject
    private SenhaRepository senhaRepository;

    public SenhaFormatada gerar(TipoSenhaEnum tipoSenhaEnum) {

        SenhaFormatada senhaFormatada;
        if(tipoSenhaEnum.equals(TipoSenhaEnum.PREFERENCIAL)) {
            Integer senha = (Integer) senhaPreferencial.gerar();
            senhaRepository.salvar(senhaPreferencial);
            senhaFormatada =  SenhaFormatada.of(senha, TipoSenhaEnum.PREFERENCIAL);
        } else {
            Integer senha = (Integer) senhaNormal.gerar();
            senhaRepository.salvar(senhaNormal);
            senhaFormatada= SenhaFormatada.of(senha, TipoSenhaEnum.NORMAL);
        }

        return senhaFormatada;
    }

    public SenhaFormatada chamar(IUsuario usuario) {

        return senhaPreferencial.isEmpty() ?
                SenhaFormatada.of((Integer)senhaNormal.chamar(usuario), TipoSenhaEnum.NORMAL)
                : SenhaFormatada.of((Integer)senhaPreferencial.chamar(usuario), TipoSenhaEnum.PREFERENCIAL);
    }
}
