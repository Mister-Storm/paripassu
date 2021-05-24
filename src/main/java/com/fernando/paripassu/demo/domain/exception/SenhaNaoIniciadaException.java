package com.fernando.paripassu.demo.domain.exception;

public class SenhaNaoIniciadaException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SenhaNaoIniciadaException (String mensage){
        super(String.format("Não há senhas do tipo %s a serem chamadas", mensage));
    }
}
