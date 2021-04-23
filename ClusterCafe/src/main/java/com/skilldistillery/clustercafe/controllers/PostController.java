package com.skilldistillery.clustercafe.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.clustercafe.entities.Post;
import com.skilldistillery.clustercafe.services.PostService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class PostController {
	
	@Autowired
	private PostService postSvc;
	
	@GetMapping("posts") 
	public List<Post> index(HttpServletRequest req, HttpServletResponse res){
//		All enabled posts
		return postSvc.index();
	}
	
	@GetMapping("posts/flagged") 
	public List<Post> indexFlagged(HttpServletRequest req, 
			HttpServletResponse res,
			Principal principal){
//		All flagged posts to an admin
		List<Post> posts = postSvc.indexFlagged(principal.getName());
		if (posts == null) {
			res.setStatus(403);
		}
		return posts;
	}
	
	@GetMapping("posts/{id}") 
	public Post show(@PathVariable int id, 
					HttpServletRequest req, 
					HttpServletResponse res) {
		Post post = postSvc.show(id);
		if (post == null) {
			res.setStatus(404);
		}
		return post;
	}
	
	@PostMapping("posts")
	public Post create(@RequestBody Post post,
			HttpServletRequest req, 
			HttpServletResponse res,
			Principal principal) {
		try {
			postSvc.create(principal.getName(), post);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(post.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			post = null;
		}
		return post;
	}
	
	@PutMapping("posts/{id}")
	public Post update(@PathVariable int id,
			@RequestBody Post post,
			HttpServletRequest req, 
			HttpServletResponse res,
			Principal principal) {
		try {
			post = postSvc.update(id, principal.getName(), post);
			if (post == null) {
				res.setStatus(404);
			}
			res.setStatus(200);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(post.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			post = null;
		}
		return post;
	}
	
	@DeleteMapping("posts/{id}")
	public void destory(@PathVariable int id, 
					HttpServletRequest req, 
					HttpServletResponse res,
					Principal principal) {
		try {
			boolean deleted = postSvc.softDelete(id, principal.getName());
			if (deleted) {
				res.setStatus(204);
			} else {
				res.setStatus(404);
			}
			
		}
		catch (Exception e) {
			System.err.println(e);
			res.setStatus(400);			
		}	
	}

}






