package com.skilldistillery.clustercafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

	List<Meeting> findByEnabledTrue();
	Meeting findByIdAndEnabledTrueAndUser_username(int id, String username);
	Meeting findByIdAndEnabledTrue(int id);
}
