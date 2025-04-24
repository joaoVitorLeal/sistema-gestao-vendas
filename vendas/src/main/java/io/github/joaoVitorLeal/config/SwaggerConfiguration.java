package io.github.joaoVitorLeal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false) // Desabilitando documentação padrão gerada pelo Swagger
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("io.github.joaoVitorLeal.rest.controller")) // definindo API (controllers) pelo pacote para serem escaneadas
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() { // Necessário para o Docket
        return new ApiInfoBuilder()
                .title("Vendas API")
                .description("API REST de sistema de gestão de vendas")
                .version("1.0")
                .contact(contact())
                .build();
    }

    private Contact contact() { // Informações de Contato
        return new Contact("João Leal", "https://github.com/joaoVitorLeal", "joaoleal98@outlook.com");
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    // Contexto de segurança
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth()) // referência
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); // Definir escopo de Authorization
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }
}
