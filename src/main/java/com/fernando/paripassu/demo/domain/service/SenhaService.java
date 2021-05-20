package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.SenhaList;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import com.fernando.paripassu.demo.domain.model.SenhaNumerica;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;


@Named
public class SenhaService {

    private SenhaList senhaListNormal;
    private SenhaList senhaListPreferencial;

    @Inject
    private SenhaRepository senhaRepository;

    private Senha<?> ultimaChamada;

    @Autowired
    public SenhaService(){
        this.senhaListNormal= new SenhaNumerica();
        this.senhaListPreferencial= new SenhaNumerica();
    }

    public Senha<?> gerar(String tipoSenhaEnum) throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha senha;
        if(tipoSenhaEnum.toUpperCase().equals(TipoSenhaEnum.PREFERENCIAL.getValor())) {
            Integer numero = (Integer) senhaListPreferencial.gerar();
            senha =  Senha.newInstance(numero, tipoSenhaEnum);
            senhaRepository.salvar(senha);
        } else {
            Integer numero = (Integer) senhaListNormal.gerar();
            senha = Senha.newInstance(numero, tipoSenhaEnum);
            senhaRepository.salvar(senha);
        }

        return senha;
    }

    public Senha chamar(IUsuario usuario) throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        ultimaChamada = senhaListPreferencial.isEmpty() ?
                Senha.newInstance((Integer) senhaListNormal.chamar(usuario), TipoSenhaEnum.NORMAL.getValor())
                : Senha.newInstance((Integer) senhaListPreferencial.chamar(usuario), TipoSenhaEnum.PREFERENCIAL.getValor());

        return ultimaChamada;
    }


    public Senha ultimaChamada() {
        return ultimaChamada;
    }

    public void reiniciarSenhas(IUsuario usuario) throws UsuarioNaoAutorizadoException {
            senhaListNormal.reiniciarSenhas(usuario);
            senhaListPreferencial.reiniciarSenhas(usuario);
            ultimaChamada = senhaListNormal.isEmpty() && senhaListPreferencial.isEmpty() ? null : ultimaChamada;
    }

    public Integer senhasNAFila() {
        return senhaListPreferencial.senhasNaFila() + senhaListNormal.senhasNaFila();
    }

}
