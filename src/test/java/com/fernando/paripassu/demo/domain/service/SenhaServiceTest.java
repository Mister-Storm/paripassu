package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.SenhaList;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SenhaServiceTest {

    @Mock
    private SenhaList senhaListNormal;
    @Mock
    private SenhaList senhaListPreferencial;
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
    public void deveGerarSenhaPreferencial() {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.PREFERENCIAL);

        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());

    }

    @Test
    public void deveGerarSenhaNormal() {

        Senha<Integer> senha = (Senha<Integer>) senhaService.gerar(TipoSenhaEnum.NORMAL);

        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());

    }

    @Test
    public void deveRetornarSenhaPreferencialQuandoHouverClientePreferencialNaFilaDeAtendimento() {

        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.FALSE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(new Senha<Integer>(1, TipoSenhaEnum.PREFERENCIAL));
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarSenhaNormalQuandoNaoHouverClientePreferencialNaFilaDeAtendimento() {

        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarUltimoNumeroChamado() {
        when(senhaListPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaListPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        Senha<Integer> senha = senhaService.chamar(Mockito.any(IUsuario.class));
        Senha<Integer> ultimaChamada = senhaService.ultimaChamada();
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
        assertTrue(senha.equals(ultimaChamada));
    }

    @Test
    public void deveLimparAsListasDeSenhas() {

        senhaService.gerar(TipoSenhaEnum.NORMAL);
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL);
        senhaService.gerar(TipoSenhaEnum.NORMAL);
        senhaService.gerar(TipoSenhaEnum.PREFERENCIAL);
        senhaService.chamar(Mockito.any(IUsuario.class));
        senhaService.reiniciarSenhas(Mockito.any(IUsuario.class));

        assertTrue(senhaService.ultimaChamada() == null);

    }

}