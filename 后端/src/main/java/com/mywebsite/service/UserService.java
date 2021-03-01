package com.mywebsite.service;

import com.mywebsite.dto.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<User>> getAll();

    int add(User user);

    int deleteByUserName(@NotNull String name);

    int updatePwdByUsername(@NotNull String username, @NotNull String pwd);

    int updateRoleByUsername(@NotNull String username, @NotNull String role);

    int updateUsername(@NotNull String username, @NotNull String newusername);

    Optional<User> getUserByUserName(@NotNull String name);

    int updateStatus(@NotNull String username, @NotNull Integer status);
}
