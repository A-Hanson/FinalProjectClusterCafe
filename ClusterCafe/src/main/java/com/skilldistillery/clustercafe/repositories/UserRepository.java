package com.skilldistillery.clustercafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	List<User> findByMeetings_Id(int id);
}
