package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.api.dto.SenhaFormatada;
import com.fernando.paripassu.demo.domain.enuns.TipoSenhaEnum;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/senhas")
public class SenhaControler {

    @Inject
    SenhaService service;

    @PostMapping
    public ResponseEntity<?> gerar(String tipoDeSenha) {
//        Senha<Integer> senha = (Senha<Integer>) service.gerar(TipoSenhaEnum.valueOf(tipoDeSenha));
//        SenhaFormatada senhaFormatada= SenhaFormatada.of((Integer)senha.getSenha(), senha.isSenhaPreferencial() ? TipoSenhaEnum.PREFERENCIAL :
//                TipoSenhaEnum.NORMAL);
        return new ResponseEntity<>("ss", HttpStatus.CREATED);

    }
}
