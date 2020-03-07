package com.github.snakerflow.plugin.starter.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.snakerflow.plugin.starter.example.dao")
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
