package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.*;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account created = accountService.register(account);

        if (created == null)
            return ResponseEntity.badRequest().build();
        else if (created.getAccountId() == 0)
            return ResponseEntity.status(409).build();
        else
            return ResponseEntity.ok(created);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginInfo) {
        Account found = accountService.login(loginInfo.getUsername(), loginInfo.getPassword());

        if (found == null)
            return ResponseEntity.status(401).build();
        else
            return ResponseEntity.ok(found);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message saved = messageService.createMessage(message);
        
        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(saved);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message msg = messageService.getMessageById(id);

        if (msg == null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id) {
        int rows = messageService.deleteMessageById(id);

        if (rows == 0)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok(rows);
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int id, @RequestBody Map<String, String> body) {
        String newText = body.get("messageText");
        int rows = messageService.updateMessage(id, newText);

        if (rows == 0)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(rows);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable int accountId) {
        return messageService.getMessagesByAccountId(accountId);
    }






}
