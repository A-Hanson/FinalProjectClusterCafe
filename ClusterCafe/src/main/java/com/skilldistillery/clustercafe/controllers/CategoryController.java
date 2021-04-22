package com.skilldistillery.clustercafe.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.clustercafe.entities.Category;
import com.skilldistillery.clustercafe.services.CategoryService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService catSvc;
	
	@GetMapping("categories")
	public List<Category> index(HttpServletRequest req, HttpServletResponse res){
		return catSvc.index();
	}

}
