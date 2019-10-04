package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.forms.LoginForm;
import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public String login(@RequestBody LoginForm request){
        if(request.login == null || request.login.equals("") || request.password == null || request.password.equals("")){
            return "Preencha os campos corretamente!";
        }
        User user = userService.findByLoginAndPassword(request.login, request.password);
        if(user == null || user.getId() == null || user.getId() == 0){
            return "Usuário e/ou Senha incorretos";
        }
        return new Gson().toJson(user);

    }

    @PostMapping(value = "/user/insert")
    public String insertUser(@RequestBody User request){
        User user = new User(request.getName(), request.getLogin(), request.getPassword());
        userService.saveUser(user);
        if(user.getId() == null || user.getId() == 0){
            return "Não foi possível salvar o item.";
        }
        HashMap<String, Long> map = new HashMap<>();
        String sb = "UserId";
        map.put(sb, user.getId());
        return new Gson().toJson(map);
    }

    @PostMapping(value = "/user/update")
    public String updateUser(@RequestBody User request){
        User update = userService.findByLogin(request.getLogin());
        if(update.getLogin()!= null) {
            userService.saveUser(update);
            return update.getId() != null ? "Atualizado!" : "Problemas ao atualizar.";
        }else{
            return "Esse login não existe.";
        }
    }

    @PostMapping(value = "/user/delete")
    public String deleteUser(@RequestParam Long id){
        if(id == 0){
            return "Preencha os campos corretamente";
        }
        userService.deleteUser(id);
        return "Usuário deletado.";
    }

}
