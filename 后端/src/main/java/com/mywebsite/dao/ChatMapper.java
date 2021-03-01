package com.mywebsite.dao;

import com.mywebsite.dto.Chat;
import com.mywebsite.dto.ChatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatMapper {
    int insertSelective(Chat record);

    List<Chat> selectByExample(ChatExample example);
}