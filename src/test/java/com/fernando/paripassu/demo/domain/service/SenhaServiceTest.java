package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.enuns.TipoUsuarioEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.model.*;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SenhaServiceTest {

    @Mock
    private SenhaNumerica senhaListNormal;
    @Mock
    private SenhaNumerica senhaListPreferencial;
    @Mock
    private SenhaRepository senhaRepository;

    @Spy
    @InjectMocks
    private SenhaService senhaService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveGerarSenhaPreferencial() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());

        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());

    }

    @Test
    public void deveGerarSenhaNormal() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());

        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());

    }

    @Test
    public void deveLAncarExcecaoQuandoTipoSenhaForIncorreto() {
        assertThrows(TipoSenhaEnumException.class, () -> senhaService.gerar("Y"));
    }

    @Test
    public void deveRetornarSenhaPreferencialQuandoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.FALSE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarSenhaNormalQuandoNaoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarUltimoNumeroChamado()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {
        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        Senha<Integer> ultimaChamada = senhaService.ultimaChamada();
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
        assertTrue(senha.equals(ultimaChamada));
    }

    @Test
    public void deveLimparAsListasDeSenhas()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.chamar(Mockito.any(IUsuario.class));
        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListNormal.isEmpty()).thenReturn(Boolean.TRUE);
        senhaService.reiniciarSenhas(Usuario.newInstance("", TipoUsuarioEnum.GERENTE.toString()));

        assertTrue(senhaService.ultimaChamada() == null);

    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoAutorizadoChamarSenha() throws UsuarioNaoAutorizadoException {

        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListNormal.chamar(Mockito.any(IUsuario.class))).thenCallRealMethod();

        assertThrows(UsuarioNaoAutorizadoException.class,
                () -> senhaService.chamar(Usuario.newInstance("", "cliente")));
    }

}