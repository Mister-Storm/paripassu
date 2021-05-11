package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import com.fernando.paripassu.demo.dto.SenhaFormatada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SenhaServiceTest {

    @Mock
    private Senha senhaNormal;
    @Mock
    private Senha senhaPreferencial;
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
    public void deveGerarSenha() {
        assertTrue(senhaService.gerar(TipoSenhaEnum.PREFERENCIAL) != null);

    }

    @Test
    public void deveRetornarSenhaPreferencialQuandoHouverClientePreferencialNaFilaDeAtendimento() {

        when(senhaPreferencial.isEmpty()).thenReturn(Boolean.FALSE);
        when(senhaPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        SenhaFormatada senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertTrue(senha.isSenhaPreferencial());
    }

    @Test
    public void deveRetornarSenhaNormalQuandoNaoHouverClientePreferencialNaFilaDeAtendimento() {

        when(senhaPreferencial.isEmpty()).thenReturn(Boolean.TRUE);
        when(senhaPreferencial.chamar(Mockito.any(IUsuario.class))).thenReturn(1);
        SenhaFormatada senha = senhaService.chamar(Mockito.any(IUsuario.class));
        assertTrue(senha != null);
        assertFalse(senha.isSenhaPreferencial());
    }

}