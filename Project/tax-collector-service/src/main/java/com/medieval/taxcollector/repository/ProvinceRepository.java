package com.medieval.taxcollector.repository;

import com.medieval.taxcollector.domain.Province;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Optional<Province> findByNameIgnoreCase(String name);
}
