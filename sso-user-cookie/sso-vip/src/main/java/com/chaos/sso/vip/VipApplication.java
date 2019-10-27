package com.chaos.sso.vip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import	java.util.ResourceBundle.Control;

/**
 * @author Chaos
 */
@SpringBootApplication
public class VipApplication {
    public static void main(String[] args) {
        SpringApplication.run(VipApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

