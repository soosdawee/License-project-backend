package com.license.backend.repository;

import com.license.backend.domain.model.TableData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableDataRepository extends JpaRepository<TableData, Integer> {
}
