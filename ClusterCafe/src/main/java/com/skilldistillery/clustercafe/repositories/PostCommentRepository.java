package com.skilldistillery.clustercafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
	List<PostComment> findByEnabledTrue();
	List<PostComment> findByEnabledTrueAndPost_Id(int id);
	PostComment findByIdAndEnabledTrueAndPost_Id(int commentId, int postId);
	PostComment findByIdAndEnabledTrueAndPost_IdAndUser_username(int commentId, int postId, String username);

}
