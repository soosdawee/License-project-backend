package com.license.backend.repository;

import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisualizationRepository extends JpaRepository<Visualization, Integer> {

    @EntityGraph(attributePaths = {"user"})
    List<Visualization> findVisualizationsByUser(User user);

    @EntityGraph(attributePaths = {"user", "user.visualizations"})
    List<Visualization> findAll();

}
