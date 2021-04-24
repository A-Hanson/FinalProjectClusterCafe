package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.PostComment;

public interface PostCommentService {
	List<PostComment> index();
	List<PostComment> indexFlagged(String username);
	List<PostComment> indexByPost(int id);
	PostComment show(int commentId, int postId);
	PostComment create(int postId, String username, PostComment postComment);
	PostComment update(int commentId, int postId, String username, PostComment postComment);
	boolean softDelete(int commentId, int postId, String username);
}
