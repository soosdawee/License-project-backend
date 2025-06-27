package com.license.backend.repository;

import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VisualizationRepository extends JpaRepository<Visualization, Integer> {

    @EntityGraph(attributePaths = {"user", "visualizationModel"})
    List<Visualization> findVisualizationsByUser(User user);

    @EntityGraph(attributePaths = {"user", "user.visualizations"})
    List<Visualization> findAll();

    @Query("""
        SELECT DISTINCT v FROM Visualization v
        JOIN FETCH v.user
        LEFT JOIN FETCH v.likedByUsers
        WHERE v.isShared = true
    """)
    List<Visualization> findSharedVisualizations();

    @Query("""
    SELECT DISTINCT v FROM Visualization v
    JOIN FETCH v.user u
    LEFT JOIN FETCH v.likedByUsers
    WHERE v.isShared = true AND u.userId = :userId
""")
    List<Visualization> findSharedVisualizationsByUserId(@Param("userId") Integer userId);

    @EntityGraph(attributePaths = "user")
    Optional<Visualization> findById(Integer id);

    @Query("""
    SELECT DISTINCT v FROM Visualization v
    JOIN FETCH v.user
    LEFT JOIN FETCH v.likedByUsers
    WHERE v.isReported = true
""")
    List<Visualization> findReportedVisualizations();

    @Query("""
    SELECT DISTINCT v FROM Visualization v
    JOIN FETCH v.user
    LEFT JOIN FETCH v.likedByUsers
    WHERE v.wasReviewedNegatively = true
""")
    List<Visualization> findNegativelyReviewedVisualizations();


}
