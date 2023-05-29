package com.iyzico.challenge.configuration;

import com.iyzipay.Options;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class IyzicoConfiguration {

    @Value("${rest.iyzico.api-key}")
    private String apiKey;

    @Value("${rest.iyzico.secret-key}")
    private String secretKey;

    @Value("${rest.iyzico.url}")
    private String baseUrl;

    @Bean
    public Options getOptions() {
        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(secretKey);
        options.setBaseUrl(baseUrl);
        return options;
    }
}
