package com.allane.leaseadmin.config;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

@ConfigurationProperties("openapi")
public record OpenApiConfig(String devUrl, String prodUrl) {

    @Bean
    public OpenAPI leaseOpenAPI() {
        Server server = new Server();

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");
        Info info = new Info()
                .title("Lease administration")
                .version("1.0")
                .description("This API exposes endpoints to manage vehicle leasing.")
                .license(mitLicense);

        if (devUrl != null) {
            server.setUrl(devUrl);
            server.setDescription("Server URL in Development environment");
        }

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
