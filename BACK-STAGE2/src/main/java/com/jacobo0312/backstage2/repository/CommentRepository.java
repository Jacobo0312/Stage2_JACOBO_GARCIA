package com.jacobo0312.backstage2.repository;

import com.jacobo0312.backstage2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {


}
