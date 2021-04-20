package com.skilldistillery.clustercafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
