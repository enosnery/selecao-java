package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.forms.LoginForm;
import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import com.enosnery.RestAPI.utils.Constants;
import com.google.gson.Gson;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public HashMap<String, Object> login(@RequestBody LoginForm request){
        HashMap<String, Object> response = new HashMap<>();
        if(request.login == null || request.login.equals("") || request.password == null || request.password.equals("")){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_BLANK_FIELDS);
        }else {
            User user = userService.findByLoginAndPassword(request.login, request.password);
            if (user == null || user.getId() == null || user.getId() == 0) {
                response.put(Constants.CODE, Response.SC_UNAUTHORIZED);
                response.put(Constants.MESSAGE, Constants.REQUEST_UNAUTHORIZED);
            }else{
                response.put(Constants.CODE, Response.SC_OK);
                response.put(Constants.RESPONSE, user);
            }
        }
        return response;
    }

    @PostMapping(value = "/user")
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

    @PutMapping(value = "/user")
    public String updateUser(@RequestBody User request){
        User update = userService.findByLogin(request.getLogin());
        if(update.getLogin()!= null) {
            userService.saveUser(update);
            return update.getId() != null ? "Atualizado!" : "Problemas ao atualizar.";
        }else{
            return "Esse login não existe.";
        }
    }

    @DeleteMapping(value = "/user")
    public String deleteUser(@RequestParam Long id){
        if(id == 0){
            return "Preencha os campos corretamente";
        }
        userService.deleteUser(id);
        return "Usuário deletado.";
    }

}
