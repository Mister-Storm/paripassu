package com.fernando.paripassu.demo.infrastructure.provider;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class SenhaRepositoryImpl implements SenhaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaRepositoryImpl.class);

    @Value("${arquivo}")
    String path;//= "/home/fernando/Downloads/senhasProvider.txt";

    File file;

    public void iniciarFile() {
        file = new File(path);
    }
    @Override
    public void salvarUltimaChamada(Senha<?> senha) {



        try (ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream(file))){
            output.writeObject(senha);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void salvarTamanhoSenhaNormal(Integer tamanho) {
        try (ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream(file))){
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
