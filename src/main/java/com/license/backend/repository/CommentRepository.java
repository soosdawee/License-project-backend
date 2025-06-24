package com.license.backend.repository;

import com.license.backend.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.replies LEFT JOIN FETCH c.author")
    List<Comment> findAllWithRepliesAndAuthor();

    List<Comment> findByVisualization_VisualizationId(Integer visualizationId);


    //List<Comment> findByVisualizationIdAndParentIsNullOrderByCreatedAtDesc(Integer visualizationId);

}
