package com.skilldistillery.clustercafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.clustercafe.services.UserService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class UserController {

	@Autowired
	private UserService userSvc;
	
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}
}
