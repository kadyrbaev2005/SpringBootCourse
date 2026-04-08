package com.medieval.taxcollector.client;

import com.medieval.taxcollector.client.dto.OracleStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "oracle-service",
        url = "${oracle.service.url}",
        fallback = OracleClientFallback.class)
public interface OracleClient {

    @GetMapping("/api/v1/oracle/status")
    OracleStatusDto getStatus();
}
