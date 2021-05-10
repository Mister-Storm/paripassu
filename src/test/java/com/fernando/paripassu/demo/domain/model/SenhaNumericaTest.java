package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.model.exception.UsuarioNaoAutorizadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class SenhaNumericaTest {

    private static final Integer ELEMENTO_UM = 1;
    private static final Integer ELEMENTO_DOIS = 2;
    private static final Integer ZERO_ELEMENTOS = 0;
    private static final Integer UM_ELEMENTO = 1;
    private static final Integer DOIS_ELEMENTOS = 2;

    Senha<?> senhas;

    @BeforeEach
    public void setup() {
        senhas = new SenhaNumerica();
    }

    @Test
    public void deveGerarSenhaQuandoFilaVazia() {
        assertTrue(senhas.gerar().equals(ELEMENTO_UM));
    }

    @Test
    public void deveGerarSenhaQuandoFilaNaoVazia() {
        senhas.gerar();

        assertTrue(senhas.gerar().equals(ELEMENTO_DOIS));
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEGerenteEChamar() {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_CLIENTE);

        assertThrows(UsuarioNaoAutorizadoException.class, () -> senhas.chamar(usuario));
    }

    @Test
    public void naoDeveLancarExcecaoQuandoUsuarioEGerenteEChamar() {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertDoesNotThrow(() -> senhas.chamar(usuario));
        assertThat(senhas.senhasNaFila(), is(ELEMENTO_UM));
    }

    @Test
    public void deveLDevolverPrimeiroItemDaFilaQuandoChamar() {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertThat(senhas.chamar(usuario), is(UM_ELEMENTO));
    }

    @Test
    public void naoDeveRetirarElementoQuandoInvocadoUltimaChamada() {
        senhas.gerar();
        senhas.gerar();

        assertTrue(senhas.ultimaChamada().equals(ELEMENTO_UM));
        assertThat(senhas.senhasNaFila(), is(DOIS_ELEMENTOS));
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEGerenteEReiniciar() {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_CLIENTE);
        senhas.gerar();
        senhas.gerar();

        assertThrows(UsuarioNaoAutorizadoException.class, () -> senhas.reiniciarSenhas(usuario));
        assertThat(senhas.senhasNaFila(), is(DOIS_ELEMENTOS));
    }

    @Test
    public void naoDeveLancarExcecaoQuandoUsuarioEGerenteEReiniciar() {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertDoesNotThrow(() -> senhas.reiniciarSenhas(usuario));
        assertThat(senhas.senhasNaFila(), is(ZERO_ELEMENTOS));
    }

}