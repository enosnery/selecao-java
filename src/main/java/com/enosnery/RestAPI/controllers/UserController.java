package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.forms.LoginForm;
import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public Long login(@RequestBody LoginForm request){
        return userService.findByLoginAndPassword(request.login, request.password);

    }

    @PostMapping(value = "/user/insert")
    public Long newUser(@RequestBody User request){
        User user = new User(request.getName(), request.getLogin(), request.getPassword());
        userService.saveUser(user);
        return user.getId();
    }

}
