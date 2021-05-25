package com.fernando.paripassu.demo.api;

import com.fernando.paripassu.demo.domain.exception.TipoSenhaEnumException;
import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SenhaControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SenhaService senhaService;

    private static final String PAYLOAD_PREFERENCIAL = "{ \"tipoDeSenha\": \"p\" }";
    private static final String PAYLOAD_NORMAL = "{\"tipoDeSenha\": \"n\"}";
    private static final String PAYLOAD_INVALIDO = "{\"tipoDeSenha\": \"S\"}";

    @Test
    public void deveRetornarNovaSenhaPreferencial() throws Exception {

        Mockito.doReturn(Senha.newInstance(1, "P"))
                .when(senhaService).gerar(Mockito.any(String.class));

        mockMvc.perform(post("/senhas")
                .content(PAYLOAD_PREFERENCIAL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("P")));
    }

    @Test
    public void deveRetornarNovaSenhaNormal() throws Exception {

        Mockito.doReturn(Senha.newInstance(1, "N"))
                .when(senhaService).gerar(Mockito.any(String.class));

        mockMvc.perform(post("/senhas").content(PAYLOAD_NORMAL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("N")));
    }

    @Test
    public void deveRetornarErroComTipoDeSenhaIncorreto() throws Exception {

        Mockito.doThrow(new TipoSenhaEnumException("S")).when(senhaService)
                .gerar(Mockito.any(String.class));
        mockMvc.perform(post("/senhas").content(PAYLOAD_INVALIDO)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("O tipo S não é um tipo de senha válido")));
    }

}