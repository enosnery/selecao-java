package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        User update = userRepository.findById(user.getId()).get();
        update.setLogin(user.getLogin());
        update.setName(user.getName());
        userRepository.saveAndFlush(update);
        return update;
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User findById(Long id){
        return userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }


}
