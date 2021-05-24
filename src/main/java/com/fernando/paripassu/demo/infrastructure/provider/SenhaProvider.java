package com.fernando.paripassu.demo.infrastructure.provider;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;

public class SenhaProvider implements SenhaRepository {

    @Override
    public void salvarUltimaChamada(Senha<?> senha) {

    }

    @Override
    public void reiniciarSenhas(IUsuario usuario) {

    }

    @Override
    public Senha<?> recuperarUltimaSenhaNormalChamada() {
        return null;
    }

    @Override
    public Integer recuperarTamanhoSenhaNormal() {
        return null;
    }

    @Override
    public Senha<?> recuperarUltimaSenhaPreferencialChamada() throws TipoUsuarioEnumException, TipoSenhaEnumException {
        return Senha.newInstance(4, TipoSenhaEnum.PREFERENCIAL.getValor());
    }

    @Override
    public Integer recuperarTamanhoSenhaPreferencial() {
        return 5;
    }
}
