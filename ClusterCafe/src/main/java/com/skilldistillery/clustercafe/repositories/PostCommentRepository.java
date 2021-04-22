package com.skilldistillery.clustercafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

}
