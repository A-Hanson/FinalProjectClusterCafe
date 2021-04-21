package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Post;
import com.skilldistillery.clustercafe.entities.User;
import com.skilldistillery.clustercafe.repositories.PostRepository;
import com.skilldistillery.clustercafe.repositories.UserRepository;

@Transactional
@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<Post> index() {
		return postRepo.findByEnabledTrue();
	}

	@Override
	public Post show(int id, String username) {
		return postRepo.findByIdAndEnabledTrueAndUser_Username(id, username);
	}

	@Override
	public Post create(String username, Post post) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			post.setUser(user);
			if (post.getEnabled() == null) {
				post.setEnabled(true);
			}
			if (post.getFlagged() == null) {
				post.setFlagged(false);
			}
			postRepo.saveAndFlush(post);
		} else {
			post = null;
		}
		return post;
	}

	@Override
	public Post update(int id, String username, Post post) {
		Post managedPost = postRepo.findByIdAndEnabledTrueAndUser_Username(id, username);
		if (managedPost != null) {
			if (post.getEnabled() != null) {
				managedPost.setEnabled(post.getEnabled());
			}
			if (post.getFlagged() != null) {
				managedPost.setFlagged(post.getFlagged());
			}
			if (post.getCategory() != null) {
				managedPost.setCategory(post.getCategory());
			}
			if (post.getContent() != null && post.getContent().length() > 0) {
				managedPost.setContent(post.getContent());
			}
			if (post.getTitle() != null && post.getTitle().length() > 0) {
				managedPost.setTitle(post.getTitle());
			}
		}
		return managedPost;
	}

	@Override
	public boolean softDelete(int id, String username) {
		boolean deleted = false;
		if (postRepo.findByIdAndEnabledTrueAndUser_Username(id, username) != null) {
			postRepo.deleteById(id);
			deleted = true;
		}
		return deleted;
	}

}
