package com.mywebsite.dao;

import com.mywebsite.dto.User;
import com.mywebsite.dto.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByUserName(String username);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updatePwdByUsername(@Param("username") String username, @Param("password") String pwd);

    int updateRoleByUsername(@Param("username") String username, @Param("role") String role);

    int updateUsername(@Param("username") String username, @Param("newusername") String newusername);

    User selectByUserName(@Param("username") String username);

    int updateStatus(@Param("username") String username, @Param("status") Integer status);
}