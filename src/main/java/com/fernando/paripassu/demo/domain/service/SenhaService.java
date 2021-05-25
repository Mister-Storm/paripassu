package com.fernando.paripassu.demo.domain.service;

import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.SenhaNaoIniciadaException;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.SenhaList;
import com.fernando.paripassu.demo.domain.model.SenhaNumerica;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;


@Named
public class SenhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaService.class);
    private final SenhaList<Integer> senhaListNormal;
    private final SenhaList<Integer> senhaListPreferencial;

    @Inject
    private SenhaRepository senhaRepository;

    private Senha<?> ultimaChamadaNormal;
    private Senha<?> ultimaChamadaPreferencial;
    
    public SenhaService(){
        this.senhaListNormal= new SenhaNumerica();
        this.senhaListPreferencial= new SenhaNumerica();

    }

    public Senha<?> gerar(String tipoSenhaEnum) {

        Senha<?> senha;
        LOGGER.info("Gerando senha do tipo {}", tipoSenhaEnum);
        if(tipoSenhaEnum.toUpperCase().equals(TipoSenhaEnum.PREFERENCIAL.getValor())) {
            Integer numero = senhaListPreferencial.gerar();
            senha =  Senha.newInstance(numero, tipoSenhaEnum);
            LOGGER.info("Senha preferencial gerada: {}", numero);
            senhaRepository.salvarTamanhoSenhaPreferencial(senhaListPreferencial.senhasNaFila());
        } else {
            Integer numero = senhaListNormal.gerar();
            senha = Senha.newInstance(numero, tipoSenhaEnum);
            LOGGER.info("Senha normal gerada: {}", numero);
            senhaRepository.salvarTamanhoSenhaNormal(senhaListNormal.senhasNaFila());
        }
        senhaRepository.salvarUltimaChamada(senha);


        return senha;
    }

    public Senha<?> chamar(IUsuario usuario)  {

        Senha<?> ultimaChamada;
        if(senhaListPreferencial.isEmpty()) {
            ultimaChamadaNormal = Senha.newInstance(senhaListNormal.chamar(usuario),
                    TipoSenhaEnum.NORMAL.getValor());
            ultimaChamada = ultimaChamadaNormal;
            senhaRepository.salvarTamanhoSenhaNormal(senhaListNormal.senhasNaFila());

        } else {
            ultimaChamadaPreferencial = Senha.newInstance(senhaListPreferencial.chamar(usuario),
                    TipoSenhaEnum.PREFERENCIAL.getValor());
            ultimaChamada = ultimaChamadaPreferencial;
            senhaRepository.salvarTamanhoSenhaPreferencial(senhaListPreferencial.senhasNaFila());
        }
        senhaRepository.salvarUltimaChamada(ultimaChamada);

        return ultimaChamada;

    }

    public void reiniciarSenhas(IUsuario usuario) {
            senhaListNormal.reiniciarSenhas(usuario);
            senhaListPreferencial.reiniciarSenhas(usuario);
            ultimaChamadaNormal =  null;
            ultimaChamadaPreferencial = null;
            senhaRepository.reiniciarSenhas(usuario);
    }

    public Integer senhasNAFila() {
        return senhaListPreferencial.senhasNaFila() + senhaListNormal.senhasNaFila();
    }

    @PostConstruct
    private void restaurarFilas() {

        Integer  tamanhoSenhaNormal = senhaRepository.recuperarTamanhoSenhaNormal();
        Integer tamanhoSenhaPReferencial = senhaRepository.recuperarTamanhoSenhaPreferencial();

        if(tamanhoSenhaNormal != null) {
            var senhaNormal = senhaRepository.recuperarUltimaSenhaNormalChamada().getSenha();
            senhaListNormal.restaurar((Integer)senhaNormal,
                    senhaRepository.recuperarTamanhoSenhaNormal());
        }

        if(tamanhoSenhaPReferencial != null) {
            var senhaPreferencial = senhaRepository.recuperarUltimaSenhaPreferencialChamada().getSenha();
            senhaListPreferencial.restaurar((Integer)senhaPreferencial,
                    senhaRepository.recuperarTamanhoSenhaPreferencial());
        }


    }

    public Senha<?> ultimaChamada(String tipoSenhaInput) {

        Optional<TipoSenhaEnum> tipoSenhaEnum = TipoSenhaEnum.getTipoSenhaPorValor(tipoSenhaInput);
        if(tipoSenhaEnum.isEmpty()) throw new TipoSenhaEnumException(tipoSenhaInput);

        Senha<?> senha = tipoSenhaEnum.get().equals(TipoSenhaEnum.PREFERENCIAL) ? this.ultimaChamadaPreferencial()
                : this.ultimaChamadaNormal();
        if(senha==null) {
            throw new SenhaNaoIniciadaException(tipoSenhaEnum.get().toString());
        }
        return senha;
    }

    private Senha<?> ultimaChamadaNormal() {
        return ultimaChamadaNormal;
    }

    private Senha<?> ultimaChamadaPreferencial() {
        return ultimaChamadaPreferencial;
    }
}
