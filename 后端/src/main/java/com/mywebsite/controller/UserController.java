package com.mywebsite.controller;

import com.mywebsite.dto.Result;
import com.mywebsite.dto.User;
import com.mywebsite.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String USER_ROLE = "user";
    private static final String NEW_USERNAME = "newUsername";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean login(@RequestBody Map<String, String> request) {
        String username = request.get(USERNAME);
        String password = request.get(PASSWORD);
        Optional<User> opt = userService.getUserByUserName(username);
        if(opt.isPresent()) {
            User user = opt.get();
            if(password.equals(user.getPassword())) {
                int status = userService.getUserByUserName(username).get().getStatus() + 1;
                userService.updateStatus(username, status);
            }
            return password.equals(user.getPassword());
        }
        else return false;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(@RequestParam String username) {
        int status = userService.getUserByUserName(username).get().getStatus() - 1;
        userService.updateStatus(username, Math.max(status, 0));
    }

    //@RequestMapping(value = "/addStatus", method = RequestMethod.POST)
    //public void addStatus(@RequestParam String username) {
    //    int status = userService.getUserByUserName(username).get().getStatus() + 1;
    //    userService.updateStatus(username, status);
    //}

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<User> register(@RequestBody Map<String, String> request) {
        User user = new User(request.get(USERNAME), request.get(PASSWORD), USER_ROLE);
        int n = userService.add(user);
        if(0 < n) {
            user = userService.getUserByUserName(request.get(USERNAME)).get();
            return new Result<>(n, "register success", user);
        } else {
            return new Result<>(n, "register failed", user);
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public Result<User> changePassword(@RequestBody Map<String, String> request) {
        String username = request.get(USERNAME);
        String newPwd = request.get(PASSWORD);
        int n = userService.updatePwdByUsername(username, newPwd);
        User user = userService.getUserByUserName(request.get(USERNAME)).get();
        if(0 < n) {
            return new Result<>(n, "change password success", user);
        } else {
            return new Result<>(n, "change password failed", user);
        }
    }

    @RequestMapping(value = "/changeRole", method = RequestMethod.POST)
    public Result<User> changeRole(@RequestBody Map<String, String> request) {
        String username = request.get(USERNAME);
        String newRole = request.get(ROLE);
        int n = userService.updateRoleByUsername(username, newRole);
        User user = userService.getUserByUserName(request.get(USERNAME)).get();
        if(0 < n) {
            return new Result<>(n, "change role success", user);
        } else {
            return new Result<>(n, "change role failed", user);
        }
    }

    @RequestMapping(value = "/changeUsername", method = RequestMethod.POST)
    public Result<User> changeUsername(@RequestBody Map<String, String> request) {
        String username = request.get(USERNAME);
        String newUsername = request.get(NEW_USERNAME);
        int n = userService.updateUsername(username, newUsername);
        if(0 < n) {
            User user = userService.getUserByUserName(newUsername).get();
            return new Result<>(n, "change username success", user);
        } else {
            User user = userService.getUserByUserName(username).get();
            return new Result<>(n, "change username failed", user);
        }
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Result<User> resetPwd(@RequestParam String username) {
        int n = userService.updatePwdByUsername(username, "");
        User user = userService.getUserByUserName(username).get();
        if(0 < n) {
            return new Result<>(n, "reset password success", user);
        } else {
            return new Result<>(n, "reset password failed", user);
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Result<User> cancel(@RequestParam String username) {
        int n = userService.deleteByUserName(username);
        if(0 < n) {
            return new Result<>(n, "cancel success", new User(null, null, null));
        } else {
            return new Result<>(n, "cancel failed", userService.getUserByUserName(username).get());
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result<List<User>> getAll() {
        return new Result<>(userService.getAll().get());
    }
}
