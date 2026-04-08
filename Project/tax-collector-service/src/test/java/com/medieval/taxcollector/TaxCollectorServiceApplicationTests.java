package com.medieval.taxcollector;

import com.medieval.taxcollector.client.OracleClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TaxCollectorServiceApplicationTests {

    @MockBean
    private OracleClient oracleClient;

    @Test
    void contextLoads() {
    }
}
