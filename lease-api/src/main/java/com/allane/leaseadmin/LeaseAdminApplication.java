package com.allane.leaseadmin;

import com.allane.leaseadmin.config.OpenApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OpenApiConfig.class)
public class LeaseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaseAdminApplication.class, args);
    }

}
