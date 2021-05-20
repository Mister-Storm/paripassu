package com.fernando.paripassu.demo.api;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SenhaControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarNovaSenhaPreferencial() throws Exception {

        mockMvc.perform(post("/senhas").content("P"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("P")));
    }

    @Test
    public void deveRetornarNovaSenhaNormal() throws Exception {

        mockMvc.perform(post("/senhas").content("N"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("N")));
    }

    @Test
    public void deveRetornarErroComTipoDeSenhaIncorreto() throws Exception {

        mockMvc.perform(post("/senhas").content("S"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("O tipo S não é um tipo de senha válido")));
    }


}