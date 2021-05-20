package com.fernando.paripassu.demo.api.exceptionhandler;

import java.io.Serializable;

public class StandardError implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Integer status;
    private String erro;
    private Long timeStamp;

    public StandardError(Integer status, String erro, Long timeStamp) {
        super();
        this.status = status;
        this.erro = erro;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}