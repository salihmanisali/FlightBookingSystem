package com.iyzico.challenge.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI openAPI() {
    Contact salihEfe = new Contact()
      .name("Salih EFE")
      .email("salihmanisali@gmail.com");

    Info info = new Info()
      .title("Iyzico Coding Challenge")
      .description("Flight Booking System")
      .version("0.0.1")
      .contact(salihEfe);

    return new OpenAPI().info(info);
  }
}
