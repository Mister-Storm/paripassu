package com.fernando.paripassu.demo.domain.model;

import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
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
    private static final Integer ULTIMO_ELEMENTO_CHAMADO_NA_FILA = 8;
    private static final Integer PRIMEIRO_ELEMENTO_APOS_RESTAURACAO = 9;
    private static final Integer TAMANHO_DA_FILA = 4;

    SenhaList<Integer> senhas;

    @BeforeEach
    public void setup() {
        senhas = new SenhaNumerica();
    }

    @Test
    public void deveGerarSenhaQuandoFilaVazia() {
        assertEquals(senhas.gerar(), ELEMENTO_UM);
    }

    @Test
    public void deveGerarSenhaQuandoFilaNaoVazia() {
        senhas.gerar();

        assertEquals(senhas.gerar(), ELEMENTO_DOIS);
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEGerenteEChamar() throws TipoUsuarioEnumException {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_CLIENTE);

        assertThrows(UsuarioNaoAutorizadoException.class, () -> senhas.chamar(usuario));
    }

    @Test
    public void naoDeveLancarExcecaoQuandoUsuarioEGerenteEChamar() throws TipoUsuarioEnumException {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertDoesNotThrow(() -> senhas.chamar(usuario));
        assertThat(senhas.senhasNaFila(), is(ELEMENTO_UM));
    }

    @Test
    public void deveLDevolverPrimeiroItemDaFilaQuandoChamar() throws TipoUsuarioEnumException, UsuarioNaoAutorizadoException {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertThat(senhas.chamar(usuario), is(UM_ELEMENTO));
    }

    @Test
    public void naoDeveRetirarElementoQuandoInvocadoUltimaChamada() {
        senhas.gerar();
        senhas.gerar();

        assertEquals(senhas.ultimaChamada(), ELEMENTO_UM);
        assertThat(senhas.senhasNaFila(), is(DOIS_ELEMENTOS));
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEGerenteEReiniciar() throws TipoUsuarioEnumException {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_CLIENTE);
        senhas.gerar();
        senhas.gerar();

        assertThrows(UsuarioNaoAutorizadoException.class, () -> senhas.reiniciarSenhas(usuario));
        assertThat(senhas.senhasNaFila(), is(DOIS_ELEMENTOS));
    }

    @Test
    public void naoDeveLancarExcecaoQuandoUsuarioEGerenteEReiniciar() throws TipoUsuarioEnumException {
        IUsuario usuario = Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE);
        senhas.gerar();
        senhas.gerar();

        assertDoesNotThrow(() -> senhas.reiniciarSenhas(usuario));
        assertThat(senhas.senhasNaFila(), is(ZERO_ELEMENTOS));
    }

    @Test
    public void deveRestaurarFila() throws TipoUsuarioEnumException, UsuarioNaoAutorizadoException {
        senhas.restaurar(ULTIMO_ELEMENTO_CHAMADO_NA_FILA, TAMANHO_DA_FILA);
        assertThat(senhas.senhasNaFila(), is(TAMANHO_DA_FILA));
        assertThat(senhas.chamar(Usuario.newInstance(UsuarioTest.NOME_USUARIO, UsuarioTest.TIPO_USUARIO_VALIDO_GERENTE))
        , is(PRIMEIRO_ELEMENTO_APOS_RESTAURACAO));
    }

}