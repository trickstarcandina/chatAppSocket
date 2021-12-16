package com.server.chat.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.server.chat.services.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.chat.model.Message;
import com.server.chat.repositories.MessageRepository;
import com.server.chat.services.MessageService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

	private static String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replaceAll("-", "");
	private static String folder = "/"+ time + "/";
	private final MessageRepository messageRepository;
	private final MinioService minioService;

	@Override
	public Message save(Message message) {
		if (message.getContentType() != null && message.getBytes() != null) {
			String name = message.getUserId() +"-"+ LocalDateTime.now() + "." + message.getContentType();
			minioService.upload(folder, name, new ByteArrayInputStream(message.getBytes()));
			String url = folder + name;
			message.setUrl(url);
		}
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getMessagesByConversationId(Integer conversationId) {
		return messageRepository.findMessagesByConversationId(conversationId);
	}

}
