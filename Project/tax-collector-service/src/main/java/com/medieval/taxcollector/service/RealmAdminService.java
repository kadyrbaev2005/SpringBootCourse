package com.medieval.taxcollector.service;

import com.medieval.taxcollector.dto.RealmProvinceAdminDto;
import com.medieval.taxcollector.repository.ProvinceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RealmAdminService {

    private final ProvinceRepository provinceRepository;

    @Transactional(readOnly = true)
    public List<RealmProvinceAdminDto> summarizeProvinces() {
        return provinceRepository.findAll().stream()
                .map(p -> RealmProvinceAdminDto.builder()
                        .provinceId(p.getId())
                        .provinceName(p.getName())
                        .revoltRisk(p.getRevoltRisk())
                        .peasantCount(p.getPeasants() == null ? 0 : p.getPeasants().size())
                        .build())
                .toList();
    }
}
