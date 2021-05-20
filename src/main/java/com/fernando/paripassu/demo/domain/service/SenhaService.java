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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;


@Named
public class SenhaService {

    private final SenhaList<Integer> senhaListNormal;
    private final SenhaList<Integer> senhaListPreferencial;

    @Inject
    private SenhaRepository senhaRepository;

    private Senha<?> ultimaChamadaNormal;
    private Senha<?> ultimaChamadaPreferencial;

    @Autowired
    public SenhaService(){
        this.senhaListNormal= new SenhaNumerica();
        this.senhaListPreferencial= new SenhaNumerica();
    }

    public Senha<?> gerar(String tipoSenhaEnum) throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<?> senha;
        if(tipoSenhaEnum.toUpperCase().equals(TipoSenhaEnum.PREFERENCIAL.getValor())) {
            Integer numero = senhaListPreferencial.gerar();
            senha =  Senha.newInstance(numero, tipoSenhaEnum);
            senhaRepository.salvarUltimaGerada(senha);
        } else {
            Integer numero = senhaListNormal.gerar();
            senha = Senha.newInstance(numero, tipoSenhaEnum);
            senhaRepository.salvarUltimaGerada(senha);
        }

        return senha;
    }

    public Senha<?> chamar(IUsuario usuario) throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<?> ultimaChamada;
        if(senhaListPreferencial.isEmpty()) {
            ultimaChamadaNormal = Senha.newInstance(senhaListNormal.chamar(usuario),
                    TipoSenhaEnum.NORMAL.getValor());
            ultimaChamada = ultimaChamadaNormal;

        } else {
            ultimaChamadaPreferencial = Senha.newInstance(senhaListPreferencial.chamar(usuario),
                    TipoSenhaEnum.PREFERENCIAL.getValor());
            ultimaChamada = ultimaChamadaPreferencial;
        }
        senhaRepository.salvarUltimaChamada(ultimaChamada);
        return ultimaChamada;

    }


    public Senha<?> ultimaChamadaNormal() {
        return ultimaChamadaNormal;
    }

    public Senha<?> ultimaChamadaPreferencial() {
        return ultimaChamadaPreferencial;
    }

    public void reiniciarSenhas(IUsuario usuario) throws UsuarioNaoAutorizadoException {
            senhaListNormal.reiniciarSenhas(usuario);
            senhaListPreferencial.reiniciarSenhas(usuario);
            ultimaChamadaNormal = (senhaListNormal.isEmpty() && senhaListPreferencial.isEmpty()) ? null
                    : ultimaChamadaNormal;
            senhaRepository.reiniciarSenhas(usuario);
    }

    public Integer senhasNAFila() {
        return senhaListPreferencial.senhasNaFila() + senhaListNormal.senhasNaFila();
    }

    @PostConstruct
    private void restaurarFilas() {
        var senha = senhaRepository.recuperarUltimaSenhaNormalChamada().getSenha();
        senhaListNormal.restaurar((Integer)senha,
                senhaRepository.recuperarTamanhoSenhaNormal());
    }

}
