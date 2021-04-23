package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.Meeting;

public interface MeetingService {
	List <Meeting> showAllForAdmin();
	Meeting show(int id);
	Meeting create(String username, Meeting meeting);
	Meeting update(int id, String username, Meeting meeting);
	boolean softDelete(int id, String username);

}
