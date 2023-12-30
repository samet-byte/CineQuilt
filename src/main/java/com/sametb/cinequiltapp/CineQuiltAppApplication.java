package com.sametb.cinequiltapp;

import com.sametb.cinequiltapp.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.sametb.cinequiltapp.config.DefaultUsers.setAndShowDefaultAdminAndManager;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CineQuiltAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CineQuiltAppApplication.class, args);
    }
//    @Autowired
//    public void setAndGetDefaultUserTokens(AuthenticationService service) {
//        setAndShowDefaultAdminAndManager(service);
//    }
}
