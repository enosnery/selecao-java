package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/login/", method = RequestMethod.GET, produces = "application/json")
    public User login(){
        User user = new User("Enos", "teste", "teste");

        return user;
    }

    @PostMapping(value = "/user/insert")
    public int newUser(){

        return 0;
    }

}
