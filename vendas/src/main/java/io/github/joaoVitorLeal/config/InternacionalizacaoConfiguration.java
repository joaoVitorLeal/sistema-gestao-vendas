package io.github.joaoVitorLeal.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfiguration {

    // Fonte de mensagens internacionalizadas do sistema
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_pt_BR"); // arquivo-fonte
        messageSource.setDefaultEncoding("ISO-8859-1"); // Encoding que reconhece acentuações, pontuações, etc
        messageSource.setDefaultLocale(Locale.getDefault()); // Rastreia o local em que está rodando a aplicação e traduz as mensagens para língua local
        return messageSource;
    }

    /**
     * Responsável por realizar a interpolação do message-source pela mensagem
     */
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource()); // Definindo messageSource() (méto-do acima) da aplicação
        return bean;
    }
}
