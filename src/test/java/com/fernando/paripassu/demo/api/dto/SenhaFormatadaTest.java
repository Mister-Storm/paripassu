package com.fernando.paripassu.demo.api.dto;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class SenhaFormatadaTest {

    private static final Integer NUMERO_TESTE_MENOR_QUE_DEZ = 4;
    private static final Integer NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE = 49;
    private static final Integer NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE = 549;
    private static final Integer NUMERO_TESTE_MAIOR_QUE_MIL = 7_549;
    private static final Object TAMANHO_DE_CARACTERES_DA_SENHA = 5;

    @Test
    public void deveRetornarSenhaComCincoCaracteres() {
        assertThat(SenhaFormatada.of(NUMERO_TESTE_MENOR_QUE_DEZ, TipoSenhaEnum.PREFERENCIAL).toString().length(),
                is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_MENOR_QUE_DEZ, TipoSenhaEnum.NORMAL).toString().length(),
                is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE, TipoSenhaEnum.PREFERENCIAL)
                .toString().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE, TipoSenhaEnum.NORMAL)
                .toString().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE, TipoSenhaEnum.PREFERENCIAL)
                .toString().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_CEM_E_NOVECENTOS_E_NOVENTA_E_NOVE, TipoSenhaEnum.NORMAL)
                .toString().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE, TipoSenhaEnum.PREFERENCIAL)
                .toString().length(), is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_ENTRE_DEZ_E_NOVENTA_E_NOVE, TipoSenhaEnum.NORMAL).toString().length(),
                is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.PREFERENCIAL).toString().length(),
                is(TAMANHO_DE_CARACTERES_DA_SENHA));
        assertThat(SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.NORMAL).toString().length(),
                is(TAMANHO_DE_CARACTERES_DA_SENHA));
    }

    @Test
    public void deveComecarComPASenhaPreferencial() {
        Assertions.assertTrue((SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.PREFERENCIAL).toString().startsWith("P")));
    }

    @Test
    public void deveComecarComNASenhaNormal() {
        Assertions.assertTrue(SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.NORMAL).toString().startsWith("N"));
    }

    @Test
    public void deveRetornarTrueNaSenhaPreferencial() {
        Assertions.assertTrue(SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.PREFERENCIAL).isSenhaPreferencial());
    }

    @Test
    public void deveRetornarFalseNaSenhaNormal() {

        Assertions.assertFalse((SenhaFormatada.of(NUMERO_TESTE_MAIOR_QUE_MIL, TipoSenhaEnum.NORMAL).isSenhaPreferencial()));
        ;
    }

}