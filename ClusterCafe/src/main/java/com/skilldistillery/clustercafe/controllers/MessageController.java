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

import com.skilldistillery.clustercafe.entities.Message;
import com.skilldistillery.clustercafe.services.MessageService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class MessageController {
// NO AUTHENTICATION CURRENTLY
	
	@Autowired
	private MessageService messageSvc;
	
	
	
	@GetMapping("messages")
	public List<Message> index(HttpServletRequest req, HttpServletResponse res) 
		{ return messageSvc.index(); }
	
	
	@GetMapping("messages/{id}")
	public Message show(@PathVariable int id, HttpServletRequest req, HttpServletResponse res)
		 { Message message = messageSvc.show(id);
		 	if ( message == null ) 
		 		{ res.setStatus(404); }
		  return message; 
		 }
	
	
	
	@PostMapping("messages")
	public Message create(@RequestBody Message message, HttpServletRequest req, HttpServletResponse res) 
		 { 
		  	try { 
		  		  messageSvc.create(message);
				  StringBuffer url = req.getRequestURL();
				  url.append("/").append(message.getId());
				  res.setHeader("location", url.toString());
			    }
	   	    catch (Exception e) {
				  				  System.err.println(e);
					 			  res.setStatus(400);
					 			  message = null;
		 	 				    }
		   return message;
		 }			
	
	
	
	@PutMapping("messages/{id}") 
	public Message update(@PathVariable int id, @RequestBody Message message, HttpServletRequest req, HttpServletResponse res)
		 {
		 	try {
			      message = messageSvc.update(id, message);
			      	if ( message == null ) {
			      		 					 res.setStatus(404); 
			      						   }
			      		res.setStatus(200);
			      		StringBuffer url = req.getRequestURL();			
			      		url.append("/").append(message.getId());
			      		res.setHeader("location", url.toString());
				}
		 	catch  (Exception e) {
								   System.err.println(e);
						  		   res.setStatus(400);
						  		   message = null;
						  		 }
		   return message;
		 }

	@DeleteMapping("messages/{id}")
	public void destroy(@PathVariable int id, HttpServletRequest req, HttpServletResponse res)
		 {
		 	try {
		 		  boolean deleted = messageSvc.softDelete(id);
		 		  	if (deleted) {
		 		  				   res.setStatus(204);
		 		  				 } 
		 		  		    else { 
		 		  			       res.setStatus(404); 
		 		  		    	 }
		 		}
		 	
		 	catch (Exception e) {
		 						  System.err.println(e);
		 						  res.setStatus(400);			
		 						}		
		 }
}

