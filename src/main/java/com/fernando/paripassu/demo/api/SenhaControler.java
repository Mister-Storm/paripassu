package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.api.dto.SenhaFormatada;
import com.fernando.paripassu.demo.api.dto.SenhaJson;
import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.TipoUsuarioEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import com.fernando.paripassu.demo.domain.model.IUsuario;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/senhas")
public class SenhaControler {

    @Autowired
    SenhaService service;

    @PostMapping
    public ResponseEntity<?> gerar(@RequestBody String tipoDeSenha) throws TipoUsuarioEnumException, TipoSenhaEnumException {

        Senha<Integer> senha = (Senha<Integer>) service.gerar(tipoDeSenha);
        SenhaFormatada senhaFormatada= SenhaFormatada.of((Integer)senha.getSenha(), senha.isSenhaPreferencial() ? TipoSenhaEnum.PREFERENCIAL :
                TipoSenhaEnum.NORMAL);
        return new ResponseEntity<>(new SenhaJson(senhaFormatada.toString()), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<?> chamar(@RequestBody IUsuario usuario) throws UsuarioNaoAutorizadoException, TipoSenhaEnumException, TipoUsuarioEnumException {

        return new ResponseEntity(service.chamar(usuario), HttpStatus.OK);
    }

}
