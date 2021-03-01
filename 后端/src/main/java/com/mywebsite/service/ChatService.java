package com.mywebsite.service;

import com.mywebsite.dto.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<List<Chat>> getAll();

    int add(Chat chat);
}
