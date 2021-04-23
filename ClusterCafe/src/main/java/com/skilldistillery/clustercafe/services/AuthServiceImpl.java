package com.skilldistillery.clustercafe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Store;
import com.skilldistillery.clustercafe.entities.User;
import com.skilldistillery.clustercafe.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepo;
	

	@Override
	public User register(User user) {
		String encodedPW = encoder.encode(user.getPassword());
		user.setPassword(encodedPW); // only persist encoded password

		// set other fields to default values
		user.setRole("standard");
		user.setEnabled(true);
		Store store = new Store();// DEBUGGING MODE ONLY
		store.setId(1);// DEBUGGING MODE ONLY
		store.setEnabled(true);
		user.setStore(store); // DEBUGGING MODE ONLY

		userRepo.saveAndFlush(user);
		return user;

	}
}
