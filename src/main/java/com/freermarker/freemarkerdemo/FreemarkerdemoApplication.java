package com.freermarker.freemarkerdemo;

import javafx.scene.layout.Background;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.freermarker.freemarkerdemo.mapper")
public class FreemarkerdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreemarkerdemoApplication.class, args);
    }

}
