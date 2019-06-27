package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }

    public Long findByLoginAndPassword(String login, String pass){
        return userRepository.findByLoginAndPassword(login, pass).getId();
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


}
