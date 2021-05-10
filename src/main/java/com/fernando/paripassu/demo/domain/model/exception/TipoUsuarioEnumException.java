package com.fernando.paripassu.demo.domain.model.exception;

public class TipoUsuarioEnumException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TipoUsuarioEnumException (String mensage){
        super(String.format("O tipo %s não é um tipo de usuário válido", mensage));
    }
}
