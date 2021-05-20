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

//TODO criar provider e método para recuperar senhas caso o sistema caia.
class SenhaServiceTest {

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
    public void deveGerarSenhaPreferencial() throws TipoUsuarioEnumException, TipoSenhaEnumException, UsuarioNaoAutorizadoException {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());

        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
        assertTrue(senhaService.senhasNAFila().equals(1));

    }

    @Test
    public void deveGerarSenhaNormal() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());

        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
        assertTrue(senhaService.senhasNAFila().equals(1));

    }

    @Test
    public void deveLAncarExcecaoQuandoTipoSenhaForIncorreto() {

        assertThrows(TipoSenhaEnumException.class, () -> senhaService.gerar("Y"));
    }

    @Test
    public void deveRetornarSenhaPreferencialQuandoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar("P");
        Senha<Integer> senha = senhaService.chamar(Usuario.newInstance("", "gerente"));

        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarSenhaNormalQuandoNaoHouverClientePreferencialNaFilaDeAtendimento()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<Integer> senha = senhaService.chamar(Usuario.newInstance("", "gerente"));
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarUltimoNumeroChamadoDaSenhaNormal()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        Senha<Integer> senha = senhaService.chamar(Usuario.newInstance("", "gerente"));
        Senha<Integer> ultimaChamada = senhaService.ultimaChamadaNormal();

        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
        assertTrue(senha.equals(ultimaChamada));
    }

    @Test
    public void deveRetornarUltimoNumeroChamadoDaSenhaPreferencial()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        Senha<Integer> senha = senhaService.chamar(Usuario.newInstance("", "gerente"));
        Senha<Integer> ultimaChamada = senhaService.ultimaChamadaPreferencial();

        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
        assertTrue(senha.equals(ultimaChamada));
    }

    @Test
    public void deveLimparAsListasDeSenhas()
            throws UsuarioNaoAutorizadoException, TipoUsuarioEnumException, TipoSenhaEnumException {

        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.gerar(TipoSenhaEnum.NORMAL.getValor());
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL.getValor());
        senhaService.chamar(Usuario.newInstance("", TipoUsuarioEnum.GERENTE.toString()));

        senhaService.reiniciarSenhas(Usuario.newInstance("", TipoUsuarioEnum.GERENTE.toString()));

        assertTrue(senhaService.ultimaChamadaNormal() == null);

    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoAutorizadoChamarSenha() throws UsuarioNaoAutorizadoException {



        assertThrows(UsuarioNaoAutorizadoException.class,
                () -> senhaService.chamar(Usuario.newInstance("", "cliente")));
    }

}