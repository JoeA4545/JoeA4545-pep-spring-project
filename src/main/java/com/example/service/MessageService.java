package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Message;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank())
            return null;
        if (message.getMessageText().length() > 255)
            return null;
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy()))
            return null;
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public int deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public int updateMessage(int id, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255)
            return 0;
        
        Message existing = messageRepository.findById(id).orElse(null);
        if (existing == null)
            return 0;

        existing.setMessageText(newText);
        messageRepository.save(existing);
        return 1;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
