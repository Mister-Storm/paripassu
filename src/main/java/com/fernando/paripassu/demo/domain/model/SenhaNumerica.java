package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;

import java.util.ArrayDeque;
import java.util.Queue;

public class SenhaNumerica implements SenhaList<Integer> {

    private static final Integer PRIMEIRO_ITEM = 1;
    Queue<Integer> senha;
    Integer ultimoCriado;

    public SenhaNumerica() {
        senha = new ArrayDeque<>();
    }
    @Override
    public Integer gerar() {

        if(senha.isEmpty()) {
            ultimoCriado = PRIMEIRO_ITEM;
            senha.add(PRIMEIRO_ITEM);
        } else {
            ultimoCriado++;
            senha.add(ultimoCriado);
        }
        return ultimoCriado;
    }

    @Override
    public Integer chamar(IUsuario usuario) throws UsuarioNaoAutorizadoException {
        if(usuario.isGerente()) {
            return senha.isEmpty() ? null : senha.remove();
        } else {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    @Override
    public Integer ultimaChamada() {
        return senha.peek();
    }

    @Override
    public void reiniciarSenhas(IUsuario usuario) throws UsuarioNaoAutorizadoException {
        if(usuario.isGerente()) {
            senha.clear();
        } else {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    @Override
    public void restaurar(Integer inicial, Integer size) {
        for(int i = 0; i < size; i++) {
            inicial++;
            senha.add(inicial);
        }
    }

    @Override
    public Integer senhasNaFila() {
        return senha.size();
    }

    @Override
    public Boolean isEmpty() {
        return senha.isEmpty();
    }

}
