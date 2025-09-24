package com.res.server.kata_sweet_shop;

import org.springframework.boot.SpringApplication;

public class TestKataSweetShopApplication {

    public static void main(String[] args) {
        SpringApplication.from(KataSweetShopApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
