package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SenhaControlerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String PAYLOAD_PREFERENCIAL = "{ \"tipoDeSenha\": \"p\" }";
    private static final String PAYLOAD_NORMAL = "{\"tipoDeSenha\": \"n\"}";
    private static final String PAYLOAD_INVALIDO = "{\"tipoDeSenha\": \"S\"}";

    @Test
    public void deveRetornarNovaSenhaPreferencial() throws Exception {


        mockMvc.perform(post("/senhas")
                .content(PAYLOAD_PREFERENCIAL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("P")));
    }

    @Test
    public void deveRetornarNovaSenhaNormal() throws Exception {

        mockMvc.perform(post("/senhas").content(PAYLOAD_NORMAL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("N")));
    }

    @Test
    public void deveRetornarErroComTipoDeSenhaIncorreto() throws Exception {

        mockMvc.perform(post("/senhas").content(PAYLOAD_INVALIDO)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("O tipo S não é um tipo de senha válido")));
    }

//    @Test
    public void deveRetornarUltimaSenha() throws Exception {

        mockMvc.perform(post("/senhas").content(PAYLOAD_NORMAL).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/senhas").content(PAYLOAD_PREFERENCIAL).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/senhas").content(PAYLOAD_NORMAL).contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(get("/senhas")
                .content(Usuario.newInstance("", "gerente").toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("P")));
    }


}