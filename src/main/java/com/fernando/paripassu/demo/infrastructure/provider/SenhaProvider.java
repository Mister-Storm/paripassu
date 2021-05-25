package com.fernando.paripassu.demo.infrastructure.provider;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SenhaProvider implements SenhaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaProvider.class);

    private ObjectOutputStream output;

    public SenhaProvider() {
        File file = new File("/home/fernando/Downloads/senhasProvider.txt");
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e ) {
            LOGGER.error(e.getMessage());
            // lançar exceção aqui.....
        }
    }
    @Override
    public void salvarUltimaChamada(Senha<?> senha) {
        try {
            output.writeObject(senha);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void salvarTamanhoSenhaNormal(Integer tamanho) {
        try {
            output.writeObject(tamanho);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void salvarTamanhoSenhaPreferencial(Integer tamanho) {

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
