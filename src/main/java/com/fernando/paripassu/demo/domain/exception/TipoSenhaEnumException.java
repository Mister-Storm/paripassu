package com.fernando.paripassu.demo.domain.exception;

public class TipoSenhaEnumException extends Exception {

    private static final long serialVersionUID = 1L;

    public TipoSenhaEnumException (String mensage){
        super(String.format("O tipo %s não é um tipo de senha válido", mensage));
    }
}
