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

import com.skilldistillery.clustercafe.entities.Store;
import com.skilldistillery.clustercafe.services.StoreService;

@RestController
@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
public class StoreController {
	
	@Autowired
	private StoreService storeSvc;
	
	@GetMapping("stores")
	public List<Store> index(HttpServletRequest req, 
			HttpServletResponse res) {
		return storeSvc.indexEnabled();
	}
	
	@GetMapping("stores/{id}")
	public Store show(@PathVariable int id, 
			HttpServletRequest req, 
			HttpServletResponse res) {
		Store store = storeSvc.show(id);
		if (store == null) {
			res.setStatus(404);
		}
		return store;
	}
	
	@PostMapping("stores")
	public Store create(@RequestBody Store store,
			HttpServletRequest req, 
			HttpServletResponse res) {
		try {
			store = storeSvc.create(store);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(store.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			store = null;
		}
		return store;
	}
	
	@PutMapping("stores/{id}")
	public Store update(@PathVariable int id, 
			@RequestBody Store store,
			HttpServletRequest req, 
			HttpServletResponse res) {
		try {
			store = storeSvc.update(id, store);
			if (store == null) {
				res.setStatus(404);
			}
			res.setStatus(200);
			StringBuffer url = req.getRequestURL();			
			url.append("/").append(store.getId());
			res.setHeader("location", url.toString());
		}
		catch  (Exception e) {
			System.err.println(e);
			res.setStatus(400);
			store = null;
		}
		return store;
	}
	
	@DeleteMapping("stores/{id}")
	public void destroy(@PathVariable int id, 
					HttpServletRequest req, 
					HttpServletResponse res) {
		try {
			boolean deleted = storeSvc.softDelete(id);
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







