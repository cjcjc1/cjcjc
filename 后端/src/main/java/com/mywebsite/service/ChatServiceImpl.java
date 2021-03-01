package com.mywebsite.service;

import com.mywebsite.dao.ChatMapper;
import com.mywebsite.dto.Chat;
import com.mywebsite.dto.ChatExample;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    public ChatServiceImpl(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    @Override
    public Optional<List<Chat>> getAll() {
        return Optional.ofNullable(chatMapper.selectByExample(new ChatExample()));
    }

    @Override
    public int add(Chat chat) {
        return chatMapper.insertSelective(chat);
    }
}
