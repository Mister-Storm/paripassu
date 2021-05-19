package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    static final String NOME_USUARIO = "Fulano";
    static final String TIPO_USUARIO_INVALIDO = "coordenador";
    static final String TIPO_USUARIO_VALIDO_GERENTE = "GeReNtE";
    static final String TIPO_USUARIO_VALIDO_CLIENTE = "cliente";

    @Test
    public void deveLancarExcecaoQuandoNAoExistirTipoUsuario() {

        assertThrows(TipoUsuarioEnumException.class,
                () -> Usuario.newInstance(NOME_USUARIO, TIPO_USUARIO_INVALIDO));

    }

    @Test
    public void naoDeveLancarExcecaoQuandoExistirTipoUsuario() {

        assertDoesNotThrow(() -> Usuario.newInstance(NOME_USUARIO, TIPO_USUARIO_VALIDO_GERENTE));
        assertDoesNotThrow(() -> Usuario.newInstance(NOME_USUARIO, TIPO_USUARIO_VALIDO_CLIENTE));

    }

    @Test
    public void deveRetornarTrueQuandoForGerente() throws TipoUsuarioEnumException {
        IUsuario usuario= Usuario.newInstance(NOME_USUARIO, TIPO_USUARIO_VALIDO_GERENTE);
        assertTrue(usuario.isGerente());
    }

    @Test
    public void naoDeveRetornarTrueQuandoNaoForGerente() throws TipoUsuarioEnumException {
        IUsuario usuario= Usuario.newInstance(NOME_USUARIO, TIPO_USUARIO_VALIDO_CLIENTE);
        assertFalse(usuario.isGerente());
    }


}