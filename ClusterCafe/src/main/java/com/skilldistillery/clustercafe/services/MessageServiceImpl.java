package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Message;
import com.skilldistillery.clustercafe.repositories.MessageRepository;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private MessageRepository messageRepo;
	
	
	@Override
	public List<Message> index() {
		return messageRepo.findAll();
	}

	
	@Override
	public Message show(int id) {
		Message message = null;
		if (messageRepo.findById(id).isPresent()) {
			message = messageRepo.findById(id).get();
		}
		return message;
	}
	
	
	@Override
	public Message create(Message message) {
	
		if (message.getEnabled() == null) {
			message.setEnabled(true);
		}
		return messageRepo.save(message);
	}


	@Override
	public Message update(int id, Message message) {
		Message updatedMessage = null;
		if (messageRepo.findById(id).isPresent()) {
			updatedMessage = messageRepo.findById(id).get();
			if ( message.getTitle() != null ) {
				updatedMessage.setTitle(message.getTitle());
			}
			if ( message.getContent() != null ) {
				updatedMessage.setContent(message.getContent());
			}
			if ( message.getRead() != null ) {
				updatedMessage.setRead(true);
			}
			if ( message.getCreator() != null) {
				updatedMessage.setCreator(message.getCreator());
			}
		}
		return updatedMessage;
	}

	
	@Override
	public boolean softDelete(int id) {
		boolean deleted = false;
		if ( messageRepo.findById(id).isPresent()) {
			messageRepo.deleteById(id);
			deleted = true;
		}
		return deleted;
	}
}
