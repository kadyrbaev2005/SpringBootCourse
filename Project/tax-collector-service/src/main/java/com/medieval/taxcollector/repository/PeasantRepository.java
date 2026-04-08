package com.medieval.taxcollector.repository;

import com.medieval.taxcollector.domain.Peasant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeasantRepository extends JpaRepository<Peasant, Long> {
}
