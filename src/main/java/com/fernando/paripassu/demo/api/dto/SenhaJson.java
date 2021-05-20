package com.fernando.paripassu.demo.api.dto;



public class SenhaJson {

    private final String senha;

    public SenhaJson(String valor) {
        this.senha=valor;
    }

    public String getValor() {
        return senha;
    }
}
