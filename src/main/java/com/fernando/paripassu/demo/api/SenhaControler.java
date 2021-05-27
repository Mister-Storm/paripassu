package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.api.dto.SenhaFormatada;
import com.fernando.paripassu.demo.api.dto.TipoSenhaInput;
import com.fernando.paripassu.demo.api.dto.UsuarioInput;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.Usuario;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/senhas")
public class SenhaControler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaControler.class);

    @Inject
    SenhaService service;

    @PostMapping
    public ResponseEntity<?> gerar(@Valid @RequestBody TipoSenhaInput tipoDeSenha) {

        LOGGER.info("solicitada geração de senha do tipo {} as {}", tipoDeSenha.getTipoDeSenha(), LocalDateTime.now());
        var senha = (Senha<Integer>) service.gerar(tipoDeSenha.getTipoDeSenha());
        SenhaFormatada senhaFormatada= SenhaFormatada.of(senha);
        LOGGER.info("A senha formatada gerada foi: {}", senhaFormatada.getSenhaFormatada());
        return new ResponseEntity<>(senhaFormatada, HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> chamar(@Valid @RequestBody UsuarioInput usuario) {

        LOGGER.info("O usuário {} do tipo {} solicitou chamada de senha."
                , usuario.getNome(), usuario.getTipoUsuario());
        Senha<?> senha = service.chamar(Usuario.newInstance(usuario.getNome(), usuario.getTipoUsuario()));
        SenhaFormatada senhaFormatada= SenhaFormatada.of((Senha<Integer>) senha);
        LOGGER.info("A senha chamada foi: {}", senhaFormatada.getSenhaFormatada());

        return new ResponseEntity<>(senhaFormatada, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> verificarUltimaChamada(@Valid @RequestBody TipoSenhaInput tipoSenhaInput) {

        LOGGER.info("Solicitando ao service qual foi a última senha chamada");
        var senha =  service.ultimaChamada(tipoSenhaInput.getTipoDeSenha());
        LOGGER.info("A última senha formatada chamada foi: {}",senha.toString());
        return new ResponseEntity<>(SenhaFormatada.of((Senha<Integer>) senha), HttpStatus.OK);
    }

    @DeleteMapping(path = "/reiniciar")
    public ResponseEntity<?> reiniciarSenhas(@Valid @RequestBody UsuarioInput usuario) {

        LOGGER.info("O usuário {} do tipo {} solicitou que as senhas sejam reiniciadas às {}."
                , usuario.getNome(), usuario.getTipoUsuario(), LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));

        service.reiniciarSenhas(Usuario.newInstance(usuario.getNome(), usuario.getTipoUsuario()));
        LOGGER.info("Senhas reiniciadas com sucesso");

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

}
