package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.PostComment;

public interface PostCommentService {
	List<PostComment> index();
	List<PostComment> indexByComment(int id);
	PostComment show(int id);
//	PostComment create(); FINISH ME
}
