package com.skilldistillery.clustercafe.controllers;

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

import com.skilldistillery.clustercafe.entities.User;
import com.skilldistillery.clustercafe.services.UserService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class UserController {
//	NO AUTHENTICATION AT THIS TIME

	@Autowired
	private UserService userSvc;
	
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
	
	@GetMapping("users") 
	public List<User> index(HttpServletRequest req, HttpServletResponse res){
		return userSvc.index();
	}
	
	@GetMapping("users/{id}") 
	public User show(@PathVariable int id, 
					HttpServletRequest req, 
					HttpServletResponse res){
		User user = userSvc.show(id);
		if (user == null) {
			res.setStatus(404);
		}
		return user;
	}
	
	@PostMapping("users")
	public User create(@RequestBody User user,
			HttpServletRequest req, 
			HttpServletResponse res) {
		try {
			userSvc.create(user);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(user.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			user = null;
		}
		return user;
	}
	

	@PutMapping("users/{id}") 
	public User update(@PathVariable int id, 
					@RequestBody User user,
					HttpServletRequest req, 
					HttpServletResponse res){
		try {
			user = userSvc.update(id, user);
			if (user == null) {
				res.setStatus(404);
			}
			res.setStatus(200);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(user.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			user = null;
		}
		return user;
	}
	
	@DeleteMapping("users/{id}")
	public void destroy(@PathVariable int id, 
					HttpServletRequest req, 
					HttpServletResponse res) {
		try {
			boolean deleted = userSvc.softDelete(id);
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



