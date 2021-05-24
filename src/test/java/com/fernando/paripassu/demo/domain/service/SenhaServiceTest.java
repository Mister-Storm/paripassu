package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.enuns.TipoUsuarioEnum;
import com.fernando.paripassu.demo.domain.exception.SenhaNaoIniciadaException;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.model.*;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SenhaServiceTest {

    @Mock
    private SenhaRepository senhaRepository;

    @Spy
    @InjectMocks
    private SenhaService senhaService;

    SenhaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveGerarSenhaPreferencial() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<?> senha = senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());

        assertNotNull(senha);
        assertTrue(senha.isSenhaPreferencial());
        assertEquals(1, (int) senhaService.senhasNAFila());

    }

    @Test
    public void deveGerarSenhaNormal() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<?> senha = senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());

        assertNotNull(senha);
        assertFalse(senha.isSenhaPreferencial());
        assertEquals(1, (int) senhaService.senhasNAFila());

    }

    @Test
    public void deveLAncarExcecaoQuandoTipoSenhaForIncorreto() {

        assertThrows(TipoSenhaEnumException.class, () -> senhaService.gerar("Y"));
    }

    @Test
    public void deveRetornarSenhaPreferencialQuandoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        var senha = senhaService.chamar(Usuario.newInstance("", "gerente"));

        assertNotNull(senha);
        assertTrue(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarSenhaNormalQuandoNaoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        var senha = senhaService.chamar(Usuario.newInstance("", "gerente"));
        assertNotNull(senha);
        assertFalse(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarUltimoNumeroChamadoDaSenhaNormal()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException, SenhaNaoIniciadaException {

        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        var senha = senhaService.chamar(Usuario.newInstance("", "gerente"));
        var ultimaChamada = senhaService.ultimaChamada(TipoSenhaEnum.NORMAL.getValor());

        assertNotNull(senha);
        assertFalse(senha.isSenhaPreferencial());
        assertEquals(senha, ultimaChamada);
    }

    @Test
    public void deveRetornarUltimoNumeroChamadoDaSenhaPreferencial()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException, SenhaNaoIniciadaException {

        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        var senha =senhaService.chamar(Usuario.newInstance("", "gerente"));
        var ultimaChamada = senhaService.ultimaChamada(TipoSenhaEnum.PREFERENCIAL.getValor());

        assertNotNull(senha);
        assertTrue(senha.isSenhaPreferencial());
        assertEquals(senha, ultimaChamada);
    }

    @Test
    public void deveLimparAsListasDeSenhas()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException, SenhaNaoIniciadaException {

        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.chamar(Usuario.newInstance("", TipoUsuarioEnum.GERENTE.toString()));

        senhaService.reiniciarSenhas(Usuario.newInstance("", TipoUsuarioEnum.GERENTE.toString()));

        assertThrows(SenhaNaoIniciadaException.class, ()-> senhaService.ultimaChamada(TipoSenhaEnum.NORMAL.getValor()));

    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoAutorizadoChamarSenha() {

        assertThrows(UsuarioNaoAutorizadoException.class,
                () -> senhaService.chamar(Usuario.newInstance("", "cliente")));
    }

}