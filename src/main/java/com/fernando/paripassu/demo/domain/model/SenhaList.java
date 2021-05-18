package com.fernando.paripassu.demo.domain.model;

import javax.inject.Named;

@Named
public interface SenhaList<T> {


    T gerar();
    T chamar(IUsuario usuario);
    T ultimaChamada();
    void reiniciarSenhas(IUsuario usuario);
    Integer senhasNaFila();
    Boolean isEmpty();

}
