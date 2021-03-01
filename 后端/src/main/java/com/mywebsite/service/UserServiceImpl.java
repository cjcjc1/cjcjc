package com.mywebsite.service;

import com.mywebsite.dao.UserMapper;
import com.mywebsite.dto.User;
import com.mywebsite.dto.UserExample;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.ofNullable(userMapper.selectByExample(new UserExample()));
    }

    @Override
    public int add(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int deleteByUserName(String name) {
        return userMapper.deleteByUserName(name);
    }

    @Override
    public int updatePwdByUsername(String username, String pwd) {
        return userMapper.updatePwdByUsername(username, pwd);
    }

    @Override
    public int updateRoleByUsername(String username, String role) {
        return userMapper.updateRoleByUsername(username, role);
    }

    @Override
    public int updateUsername(String username, String newusername) {
        return userMapper.updateUsername(username, newusername);
    }

    @Override
    public Optional<User> getUserByUserName(String name) {
        return Optional.ofNullable(userMapper.selectByUserName(name));
    }

    @Override
    public int updateStatus(String username, Integer status) {
        return userMapper.updateStatus(username, status);
    }
}
