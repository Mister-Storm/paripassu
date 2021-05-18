package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.model.exception.UsuarioNaoAutorizadoException;

import java.util.ArrayDeque;
import java.util.Queue;

public class SenhaNumerica implements SenhaList<Integer> {

    private static final Integer PRIMEIRO_ITEM = 1;
    Queue<Integer> senha;

    public SenhaNumerica() {
        senha = new ArrayDeque<>();
    }
    @Override
    public Integer gerar() {
        Integer retorno;
        if(senha.isEmpty()) {
            retorno = PRIMEIRO_ITEM;
            senha.add(PRIMEIRO_ITEM);
        } else {
            retorno = senha.peek() + 1;
            senha.add(retorno);
        }
        return retorno;
    }

    @Override
    public Integer chamar(IUsuario usuario) {
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
    public void reiniciarSenhas(IUsuario usuario) {
        if(usuario.isGerente()) {
            senha.clear();
        } else {
            throw new UsuarioNaoAutorizadoException();
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
