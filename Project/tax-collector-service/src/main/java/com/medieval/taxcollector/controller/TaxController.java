package com.medieval.taxcollector.controller;

import com.medieval.taxcollector.dto.CollectTaxRequest;
import com.medieval.taxcollector.dto.TaxCollectionResponse;
import com.medieval.taxcollector.service.TaxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/taxes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping(path = "/collect", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaxCollectionResponse collect(@Valid @RequestBody CollectTaxRequest request) {
        return taxService.collectTax(request);
    }
}
