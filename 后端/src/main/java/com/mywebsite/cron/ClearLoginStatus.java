package com.mywebsite.cron;

import com.mywebsite.dto.User;
import com.mywebsite.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ClearLoginStatus {

    private final UserService userService;

    public ClearLoginStatus(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 1,2,3,4 * * ?")
    private void configureTasks() {
        List<User> userList = userService.getAll().get();
        for(User u : userList){
            userService.updateStatus(u.getUsername(), 0);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + " 清理登陆状态");
    }
}
