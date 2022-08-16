package com.ciandt.api.pagamento.config;

import com.ciandt.api.pagamento.exception.PagamentoNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ResponseMessage m200 = simpleMessage(200, "OK");
    private final ResponseMessage m201 = simpleMessage(201, "Created");
    private final ResponseMessage m204 = simpleMessage(204, "No content");
    private final ResponseMessage m400 = simpleMessage(400, "Argument not valid");
    private final ResponseMessage m404 = simpleMessage(404, PagamentoNotFoundException.MSG);
    private final ResponseMessage m500 = simpleMessage(500, "Internal Server Error");

    private ResponseMessage simpleMessage(int code, String message) {
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(m200,m500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(m201,m400, m500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(m200, m404, m400, m500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204, m404,m500))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ciandt.api.pagamento.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ToTal Shakes REST API")
                .description("CRUD API de Pagamentos")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Rebeca", "", "re.bfviana@gmail.com.br"))
                .build();
    }
}
