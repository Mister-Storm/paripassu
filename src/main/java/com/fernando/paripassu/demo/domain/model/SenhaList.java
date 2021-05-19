package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;

import javax.inject.Named;

@Named
public interface SenhaList<T> {


    T gerar();
    T chamar(IUsuario usuario) throws UsuarioNaoAutorizadoException;
    T ultimaChamada();
    void reiniciarSenhas(IUsuario usuario) throws UsuarioNaoAutorizadoException;
    Integer senhasNaFila();
    Boolean isEmpty();

}
