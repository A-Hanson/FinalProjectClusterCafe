package com.skilldistillery.clustercafe.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("users") 
	public List<Post> index(HttpServletRequest req, HttpServletResponse res){
//		All enabled posts
		return postSvc.index();
	}

}
