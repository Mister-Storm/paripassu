package com.fernando.paripassu.demo.domain.model;

import javax.inject.Named;

@Named
public interface Senha<T> {

    T gerar();
    T chamar(IUsuario usuario);
    T ultimaChamada();
    void reiniciarSenhas(IUsuario usuario);
    Integer senhasNaFila();
    Boolean isEmpty();

}
