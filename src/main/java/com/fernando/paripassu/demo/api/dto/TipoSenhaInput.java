package com.fernando.paripassu.demo.api.dto;

import javax.validation.constraints.NotBlank;

public class TipoSenhaInput {

    @NotBlank(message = "Campo tipoDeSenha é obrigatório")
    private String tipoDeSenha;

    public String getTipoDeSenha() {
        return tipoDeSenha;
    }

    public void setTipoDeSenha(String tipoDeSenha) {
        this.tipoDeSenha = tipoDeSenha;
    }
}
