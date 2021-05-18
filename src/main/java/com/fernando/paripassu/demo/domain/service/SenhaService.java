package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.SenhaList;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;

import javax.inject.Inject;
import javax.inject.Named;
/**
 * mudar esse cara... deve retornar um objeto de domínio.... algo tipo Senha (mudar a interface Senha para
 * SenhaList<T> e SenhaNumerica para SenhaListNumerica
 * Senha com <T> e um tipo?
 * */
@Named
public class SenhaService {

    @Inject
    private SenhaList senhaListNormal;
    @Inject
    private SenhaList senhaListPreferencial;
    @Inject
    private SenhaRepository senhaRepository;

    private Senha<?> ultimaChamada;

    public Senha<?> gerar(TipoSenhaEnum tipoSenhaEnum) {

        Senha senha;
        if(tipoSenhaEnum.equals(TipoSenhaEnum.PREFERENCIAL)) {
            Integer numero = (Integer) senhaListPreferencial.gerar();
            senha =  new Senha(numero, TipoSenhaEnum.PREFERENCIAL);
            senhaRepository.salvar(senha);
        } else {
            Integer numero = (Integer) senhaListNormal.gerar();
            senha = new Senha(numero, TipoSenhaEnum.NORMAL);
            senhaRepository.salvar(senha);
        }

        return senha;
    }

    public Senha chamar(IUsuario usuario) {

        ultimaChamada = senhaListPreferencial.isEmpty() ?
                new Senha((Integer) senhaListNormal.chamar(usuario), TipoSenhaEnum.NORMAL)
                : new Senha((Integer) senhaListPreferencial.chamar(usuario), TipoSenhaEnum.PREFERENCIAL);

        return ultimaChamada;
    }


    public Senha ultimaChamada() {
        return ultimaChamada;
    }

    public void reiniciarSenhas(IUsuario usuario) {
            senhaListNormal.reiniciarSenhas(usuario);
            senhaListPreferencial.reiniciarSenhas(usuario);
            ultimaChamada = senhaListNormal.isEmpty() && senhaListPreferencial.isEmpty() ? null : ultimaChamada;
    }
}
