package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Meeting;
import com.skilldistillery.clustercafe.entities.Store;
import com.skilldistillery.clustercafe.entities.User;
import com.skilldistillery.clustercafe.repositories.MeetingRepository;
import com.skilldistillery.clustercafe.repositories.UserRepository;

@Transactional
@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private MeetingRepository meetingRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public List<Meeting> showAllForAdmin() {
		return meetingRepo.findByEnabledTrue();
	}
	
	@Override
	public Meeting show(int id) {
		return meetingRepo.findByIdAndEnabledTrue(id);
	}

	@Override
	public Meeting create(String username, Meeting meeting) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			meeting.setUser(user);
			if (meeting.getEnabled() == null) {
				meeting.setEnabled(true);
			}
			if (meeting.getFlagged() == null) {
				meeting.setFlagged(false);
			}
			if (meeting.getStore() == null) { // DEBUGGING PURPOSES ONLY
				Store store = new Store();
				store.setId(1);
				meeting.setStore(store);
			}
			meetingRepo.saveAndFlush(meeting);
		} else {
			meeting = null;
		}
		return meeting;
	}

	@Override
	public Meeting update(int id, String username, Meeting meeting) {
		Meeting updatedMeeting = meetingRepo.findByIdAndEnabledTrueAndUser_username(id, username);
		if (updatedMeeting != null) {
			if (meeting.getEnabled() != null) {
				updatedMeeting.setEnabled(meeting.getEnabled());
			}
			if (meeting.getFlagged() != null) {
				updatedMeeting.setFlagged(meeting.getFlagged());
			}
			if (meeting.getCategory() != null) {
				updatedMeeting.setCategory(meeting.getCategory());
			}
			if (meeting.getDescription() != null) {
				updatedMeeting.setDescription(meeting.getDescription());
			}
			if (meeting.getMeetingDate() != null) {
				updatedMeeting.setMeetingDate(meeting.getMeetingDate());
			}
			if (meeting.getName() != null) {
				updatedMeeting.setName(meeting.getName());
			}
			if (meeting.getStore() != null) {
				updatedMeeting.setStore(meeting.getStore());
			}
		} else if (meetingRepo.findByIdAndEnabledTrue(id) != null) {
			updatedMeeting = meetingRepo.findByIdAndEnabledTrue(id);
			updatedMeeting.setFlagged(meeting.getFlagged());
		}
		return updatedMeeting;
	}

	@Override
	public boolean softDelete(int id, String username) {
		boolean deleted = false;
		User user = userRepo.findByUsername(username);
		Meeting userMeeting = meetingRepo.findByIdAndEnabledTrueAndUser_username(id, username);
		if (user != null && user.getRole().equals("admin") && meetingRepo.findById(id).isPresent()) {
			Meeting meeting = meetingRepo.findById(id).get();
			meeting.setEnabled(false);
			deleted = true;
		}
		else if (userMeeting != null) {
			userMeeting.setEnabled(false);
			deleted = true;
		}
		return deleted;
	}
}