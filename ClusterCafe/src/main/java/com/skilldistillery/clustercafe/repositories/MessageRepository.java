package com.skilldistillery.clustercafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.Message;

public interface MessageRepository extends  JpaRepository<Message, Integer> {

}
