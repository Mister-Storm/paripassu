package com.fernando.paripassu.demo.infrastructure.config;

import com.fernando.paripassu.demo.domain.model.Senha;
import com.fernando.paripassu.demo.domain.model.SenhaNumerica;
import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import com.fernando.paripassu.demo.infrastructure.provider.SenhaProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AplicacaoConfig {

    @Bean
    public Senha senha() {
        return new SenhaNumerica();
    }

    @Bean
    public SenhaRepository senhaRepository() {
        return new SenhaProvider();
    }
}
