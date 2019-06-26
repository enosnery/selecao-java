package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public int login(){
        User user = new User("Enos", "teste", "teste");
        user.setId(0);
        userService.saveUser(user);
        return user.getId();
    }

    @PostMapping(value = "/user/insert")
    public int newUser(){

        return 0;
    }

}
