package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }


}
