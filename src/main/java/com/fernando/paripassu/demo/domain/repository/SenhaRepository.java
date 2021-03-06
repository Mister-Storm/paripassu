package com.fernando.paripassu.demo.domain.repository;

import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;

import java.io.FileNotFoundException;

public interface SenhaRepository {

    void iniciarFile();

    void salvarUltimaChamada(Senha<?> senha);

    void salvarTamanhoSenhaNormal(Integer tamanho);

    void salvarTamanhoSenhaPreferencial(Integer tamanho);

    void reiniciarSenhas(IUsuario usuario);

    Senha recuperarUltimaSenhaNormalChamada();

    Integer recuperarTamanhoSenhaNormal();

    Senha recuperarUltimaSenhaPreferencialChamada() throws TipoUsuarioEnumException, TipoSenhaEnumException;

    Integer recuperarTamanhoSenhaPreferencial();
}
