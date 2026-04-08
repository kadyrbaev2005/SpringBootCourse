package com.medieval.taxcollector.controller;

import com.medieval.taxcollector.dto.RealmProvinceAdminDto;
import com.medieval.taxcollector.service.RealmAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    private final RealmAdminService realmAdminService;

    @GetMapping("/provinces")
    public List<RealmProvinceAdminDto> provinces() {
        return realmAdminService.summarizeProvinces();
    }
}
