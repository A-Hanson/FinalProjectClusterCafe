package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.Message;

public interface MessageService {

	public List<Message> index(); 

	public Message show(int id); 

	public Message create(Message message);

	public Message update(int id, Message message);
	
	public boolean softDelete(int id);
	
}
