package com.res.server.kata_sweet_shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class KataSweetShopApplicationTests {

    @Test
    void contextLoads() {
    }

}
