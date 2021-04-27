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

import com.skilldistillery.clustercafe.entities.Meeting;
import com.skilldistillery.clustercafe.services.MeetingService;

@CrossOrigin({"*", "http://localhost:4290"})
@RequestMapping("api")
@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetingSvc;
	
	@GetMapping("meetings")
	public List<Meeting> showAllForAdmin(HttpServletRequest req, HttpServletResponse res) {
	return meetingSvc.showAllForAdmin();
	}
	
	@GetMapping("meetings/flagged")
	public List<Meeting> showAllFlaggedForAdmin(HttpServletRequest req, 
			HttpServletResponse res,
			Principal principal) {
		List<Meeting> meetings = meetingSvc.showAllFlaggedForAdmin(principal.getName());
		if (meetings == null) {
			res.setStatus(403);
		}
		return meetings;
	}
	
	@GetMapping("meetings/{id}")
	public Meeting show(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		Meeting meeting = meetingSvc.show(id);
		if (meeting == null) {
			res.setStatus(404);
		}
		return meeting;
	}


	@PostMapping("meetings")
	public Meeting create(@RequestBody Meeting meeting, HttpServletRequest req, HttpServletResponse res, Principal principal) {
		try {
			 meetingSvc.create(principal.getName(), meeting);
			 res.setStatus(201);
			 StringBuffer url = req.getRequestURL();
			 url.append("/").append(meeting.getId());
			 res.setHeader("location", url.toString());
			}
		catch (Exception e) {
							 System.err.println(e);
							 res.setStatus(400);
							 meeting = null;
							}
		return meeting;
	}

	@PutMapping("meetings/{id}")
	public Meeting update(@PathVariable int id,
						  @RequestBody Meeting meeting,
						  HttpServletRequest req,
						  HttpServletResponse res,
						  Principal principal
						 ) {
			try { 
				meeting = meetingSvc.update(id, principal.getName(), meeting);
						if (meeting == null) {
							res.setStatus(404);
						}
					res.setStatus(200);
					StringBuffer url = req.getRequestURL();
					url.append("/").append(meeting.getId());
					res.setHeader("location", url.toString());
				 }		
		catch (Exception e) {
							 System.err.println(e);
							 res.setStatus(400);
							 meeting = null;
							}
	 return meeting;
	}
	
	@DeleteMapping("meetings/{id}")
	public void destroy(@PathVariable int id,
						HttpServletRequest req,
						HttpServletResponse res,
						Principal principal
						) {
			try {
				 boolean deleted = meetingSvc.softDelete(id, principal.getName());
					if (deleted) {
						res.setStatus(204);
					}
					else {res.setStatus(404);}
				}
		catch (Exception e) {
			System.err.println(e);
			res.setStatus(400);
		}
	}
}