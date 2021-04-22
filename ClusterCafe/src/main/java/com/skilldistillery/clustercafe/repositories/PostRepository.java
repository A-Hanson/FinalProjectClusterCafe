package com.skilldistillery.clustercafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByEnabledTrue();
	Post findByIdAndEnabledTrue(int id);
	Post findByIdAndEnabledTrueAndUser_Username(int id, String username);
}
