package com.fernando.paripassu.demo.api.exceptionhandler;

import com.fernando.paripassu.demo.domain.exception.SenhaNaoIniciadaException;
import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.exception.UsuarioNaoAutorizadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler {



    @ExceptionHandler({TipoSenhaEnumException.class, SenhaNaoIniciadaException.class})
    public ResponseEntity<?> objectNotFound(RuntimeException ex) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<?> handleContraintViolation(UsuarioNaoAutorizadoException ex) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

}