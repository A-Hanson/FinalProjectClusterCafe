package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Post;
import com.skilldistillery.clustercafe.entities.PostComment;
import com.skilldistillery.clustercafe.entities.User;
import com.skilldistillery.clustercafe.repositories.PostCommentRepository;
import com.skilldistillery.clustercafe.repositories.PostRepository;
import com.skilldistillery.clustercafe.repositories.UserRepository;

@Service
@Transactional
public class PostCommentServiceImpl implements PostCommentService {
	
	@Autowired
	private PostCommentRepository pcRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	

	@Override
	public List<PostComment> index() {
		return pcRepo.findByEnabledTrue();
	}

	@Override
	public List<PostComment> indexByPost(int id) {
		return pcRepo.findByEnabledTrueAndPost_Id(id);
	}

	@Override
	public PostComment show(int commentId, int postId) {
		return pcRepo.findByIdAndEnabledTrueAndPost_Id(commentId, postId);
	}

	@Override
	public PostComment create(int postId, String username, PostComment postComment) {
		
		if (postRepo.findById(postId).isPresent()) {
			User user = userRepo.findByUsername(username);
			if (user != null) {
				Post post = postRepo.findById(postId).get();
				postComment.setPost(post);
				postComment.setUser(user);
				if (postComment.getEnabled() == null) {
					postComment.setEnabled(true);
				}
				if (postComment.getFlagged() == null) {
					postComment.setFlagged(false);
				}
				pcRepo.saveAndFlush(postComment);
			}

		} else {
			postComment = null;
		}
		return postComment;
	}

	@Override
	public PostComment update(int commentId, int postId, String username, PostComment postComment) {
		PostComment managedComment = pcRepo.findByIdAndEnabledTrueAndPost_IdAndUser_username(commentId, postId, username);
		if (managedComment != null ) {
			managedComment.setContent(postComment.getContent());
			
		} else if (pcRepo.findByIdAndEnabledTrueAndPost_Id(commentId, postId) != null) {
			managedComment = pcRepo.findByIdAndEnabledTrueAndPost_Id(commentId, postId);
			managedComment.setFlagged(postComment.getFlagged());
		} 
		return managedComment;
	}

	@Override
	public boolean softDelete(int commentId, int postId, String username) {
		boolean deleted = false;
		User user = userRepo.findByUsername(username);
		PostComment userPostComment = pcRepo.findByIdAndEnabledTrueAndPost_IdAndUser_username(commentId, postId, username);
		if (userPostComment != null) {
			userPostComment.setEnabled(false);
			deleted = true;
		} else if (user != null && user.getRole().equals("admin") 
				&& pcRepo.findByIdAndEnabledTrueAndPost_Id(commentId, postId) != null) {
			userPostComment = pcRepo.findByIdAndEnabledTrueAndPost_Id(commentId, postId);
			userPostComment.setEnabled(false);
			deleted = true;
		}
		return deleted;
	}

}
