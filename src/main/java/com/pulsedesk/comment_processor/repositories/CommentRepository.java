package com.pulsedesk.comment_processor.repositories;

import com.pulsedesk.comment_processor.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}