package com.license.backend.repository;

import com.license.backend.domain.model.Visualization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisualizationRepository extends JpaRepository<Visualization, Integer> {
}
