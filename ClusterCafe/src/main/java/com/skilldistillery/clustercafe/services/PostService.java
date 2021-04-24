package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.Post;

public interface PostService {
	List<Post> index();
	List<Post> indexFlagged(String username);
	Post show(int id);
	Post create(String username, Post post);
	Post update(int id, String username, Post post);
	boolean softDelete(int id, String username);
}
