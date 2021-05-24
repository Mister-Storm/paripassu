package com.fernando.paripassu.demo.api.dto;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.Senha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class SenhaFormatadaTest {

    private static final Integer NUMERO_TESTE_MENOR_QUE_DEZ = 4;
    private static final Integer NUMERO_TESTE_IGUAL_A_DEZ = 10;
    private static final Integer NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE = 49;
    private static final Integer NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE = 549;
    private static final Integer NUMERO_TESTE_MAIOR_QUE_MIL = 7_549;
    private static final Object TAMANHO_DE_CARACTERES_DA_SENHA = 5;

    @Test
    public void deveRetornarSenhaComCincoCaracteres() throws TipoUsuarioEnumException, TipoSenhaEnumException {
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MENOR_QUE_DEZ,
                TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MENOR_QUE_DEZ,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_IGUAL_A_DEZ,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
    }

    @Test
    public void deveComecarComPASenhaPreferencial() throws TipoUsuarioEnumException, TipoSenhaEnumException {
        Assertions.assertTrue((SenhaFormatada.of(
                Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                        TipoSenhaEnum.PREFERENCIAL.getValor())).getSenhaFormatada().startsWith("P")));
    }

    @Test
    public void deveComecarComNASenhaNormal() throws TipoUsuarioEnumException, TipoSenhaEnumException {
        Assertions.assertTrue(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                TipoSenhaEnum.NORMAL.getValor())).getSenhaFormatada().startsWith("N"));
    }

    @Test
    public void deveRetornarTrueNaSenhaPreferencial() throws TipoUsuarioEnumException, TipoSenhaEnumException {
        Assertions.assertTrue(SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                TipoSenhaEnum.PREFERENCIAL.getValor())).isSenhaPreferencial());
    }

    @Test
    public void deveRetornarFalseNaSenhaNormal() throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Assertions.assertFalse((SenhaFormatada.of(Senha.newInstance(NUMERO_TESTE_MAIOR_QUE_MIL,
                TipoSenhaEnum.NORMAL.getValor())).isSenhaPreferencial()));

    }

}