package com.holydev.fastcase;

import com.holydev.fastcase.services.realisation.RoleService;
import com.holydev.fastcase.services.realisation.UserService;
import com.holydev.fastcase.services.interfaces.StorageService;
import com.holydev.fastcase.utilities.properties.StorageProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class FastCaseApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FastCaseApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setLogStartupInfo(false);
        springApplication.run(args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, RoleService roleService, UserService userService) {
        return (args) -> {
            roleService.init();
            userService.createDefault();
            storageService.deleteAll();
            storageService.init();
        };
    }
}
