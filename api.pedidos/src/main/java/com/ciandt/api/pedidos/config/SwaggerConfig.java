package com.ciandt.api.pedidos.config;

import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
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

    private final ResponseMessage MSG_200 = simpleMessage(200, "OK");
    private final ResponseMessage MSG_201 = simpleMessage(201, "Created");
    private final ResponseMessage MSG_204 = simpleMessage(204, "No content");
    private final ResponseMessage ARGUMENT_INVALID_400 = simpleMessage(400, "Argument not valid");
    private final ResponseMessage PEDIDO_JA_CADASTRADO_400 = simpleMessage(404, PedidoJaCadastrado.MSG);
    private final ResponseMessage MSG_404 = simpleMessage(404, PedidoNotFoundException.MSG);
    private final ResponseMessage MSG_500 = simpleMessage(500, "Internal Server Error");

    private ResponseMessage simpleMessage(int code, String message) {
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(MSG_200, MSG_500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(MSG_201, PEDIDO_JA_CADASTRADO_400, ARGUMENT_INVALID_400, MSG_500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(MSG_200, ARGUMENT_INVALID_400, MSG_404, MSG_500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ciandt.api.pedidos.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ToTal Shakes REST API")
                .description("CRUD API de Pedidos")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Rebeca", "", "re.bfviana@gmail.com.br"))
                .build();
    }
}