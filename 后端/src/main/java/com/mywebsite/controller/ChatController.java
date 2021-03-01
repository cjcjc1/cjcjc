package com.mywebsite.controller;

import com.mywebsite.dto.Chat;
import com.mywebsite.dto.Result;
import com.mywebsite.service.ChatService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result<List<Chat>> getAll() {
        return new Result<>(chatService.getAll().get());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Chat> add(@RequestBody Map<String, String> request) {
        Chat chat = new Chat(request.get("user"), request.get("content"), new Date());
        int n = chatService.add(chat);
        if(0 < n) {
            return new Result<>(n, "send chat message success", chat);
        } else {
            return new Result<>(n, "send chat message failed", new Chat(null, null, null));
        }
    }
}
