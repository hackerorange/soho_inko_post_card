package com.soho.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@SpringBootApplication
@EnableWebMvc
@CrossOrigin("*.*")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

