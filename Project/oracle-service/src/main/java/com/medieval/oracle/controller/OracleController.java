package com.medieval.oracle.controller;

import com.medieval.oracle.dto.OracleStatusDto;
import com.medieval.oracle.service.OracleStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/oracle", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OracleController {

    private final OracleStatusService oracleStatusService;

    @GetMapping("/status")
    public OracleStatusDto status() {
        return oracleStatusService.readPortents();
    }
}
