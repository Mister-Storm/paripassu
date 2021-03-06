package com.fernando.paripassu.demo.infrastructure.config;

import com.fernando.paripassu.demo.domain.repository.SenhaRepository;
import com.fernando.paripassu.demo.domain.service.SenhaService;
import com.fernando.paripassu.demo.infrastructure.provider.SenhaRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;


@Configuration
public class AplicacaoConfig {

    @Bean(initMethod = "iniciarFile")
    public SenhaRepository senhaRepository() {
        return new SenhaRepositoryImpl();
    }

    @Bean(initMethod = "restaurarFilas")
    public SenhaService senhaService() {
        return new SenhaService();
    }

    @Bean
    public Filter getCharacterEncodingFilter() {

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        return encodingFilter;
    }

}
