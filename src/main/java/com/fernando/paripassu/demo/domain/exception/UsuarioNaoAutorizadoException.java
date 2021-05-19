package com.fernando.paripassu.demo.domain.exception;

public class UsuarioNaoAutorizadoException extends Exception{

    private static final long serialVersionUID = 1L;

    public UsuarioNaoAutorizadoException(){
        super(String.format("Somente usuário com perfil gerente pode chamar senhas"));
    }
}
