package com.medieval.taxcollector.repository;

import com.medieval.taxcollector.domain.TaxRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRecordRepository extends JpaRepository<TaxRecord, Long> {
}
