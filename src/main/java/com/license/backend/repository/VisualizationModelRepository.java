package com.license.backend.repository;

import com.license.backend.domain.model.VisualizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VisualizationModelRepository extends JpaRepository<VisualizationModel, Integer> {

    @Query("SELECT vm FROM VisualizationModel vm LEFT JOIN FETCH vm.columnNames WHERE vm.visualizationModelId = :id")
    Optional<VisualizationModel> findByVisualizationModelId(@Param("id") Integer visualizationModelId);


}
