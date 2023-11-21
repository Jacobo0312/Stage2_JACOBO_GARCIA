package com.jacobo0312.backstage2.repository;

import com.jacobo0312.backstage2.model.Comment;
import com.jacobo0312.backstage2.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QueryRepository extends JpaRepository<Query, UUID> {

}
