package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.api.dto.SenhaFormatada;
import com.fernando.paripassu.demo.api.dto.TipoSenhaInput;
import com.fernando.paripassu.demo.api.dto.UsuarioInput;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.Usuario;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/senhas")
public class SenhaControler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaControler.class);

    @Autowired
    SenhaService service;

    @PostMapping
    public ResponseEntity<?> gerar(@RequestBody TipoSenhaInput tipoDeSenha) throws TipoUsuarioEnumException, TipoSenhaEnumException {

        LOGGER.info("solicitada geração de senha do tipo {} as {}", tipoDeSenha.getTipoDeSenha(), LocalDateTime.now());
        Senha<Integer> senha = (Senha<Integer>) service.gerar(tipoDeSenha.getTipoDeSenha());
        SenhaFormatada senhaFormatada= SenhaFormatada.of(senha);
        LOGGER.info("A senha formatada gerada foi: {}", senhaFormatada.getSenhaFormatada());
        return new ResponseEntity<>(senhaFormatada, HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> chamar(@RequestBody UsuarioInput usuario) throws UsuarioNaoAutorizadoException, TipoSenhaEnumException, TipoUsuarioEnumException {

        LOGGER.info("O usuário {} do tipo {} solicitou chamada de senha."
                , usuario.getNome(), usuario.getTipoUsuario());
        Senha<?> senha = service.chamar(Usuario.newInstance(usuario.getNome(), usuario.getTipoUsuario()));
        SenhaFormatada senhaFormatada= SenhaFormatada.of((Senha<Integer>) senha);
        LOGGER.info("A senha chamada foi: {}", senhaFormatada.getSenhaFormatada());

        return new ResponseEntity<>(senhaFormatada, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> verificarUltimaChamada(@RequestBody TipoSenhaInput tipoSenhaInput) {

        Senha<?> senha =  service.ultimaChamada(tipoSenhaInput.getTipoDeSenha());
        return new ResponseEntity<>(SenhaFormatada.of((Senha<Integer>) senha), HttpStatus.OK);
    }


}
