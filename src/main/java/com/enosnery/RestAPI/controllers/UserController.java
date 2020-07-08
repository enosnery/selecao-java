package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.forms.LoginForm;
import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import com.enosnery.RestAPI.utils.Constants;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public HashMap<String, Object> getUser(@RequestParam Long userId){
        HashMap<String, Object> response = new HashMap<>();
        User user = userService.findById(userId);
        if(user == null || user.getId() == null || user.getId() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.ERROR_NO_USER);
        }else{
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, user);
        }
        return response;
    }


    @PostMapping(value = "/user")
    public HashMap<String, Object> insertUser(@RequestBody User request){
        HashMap<String, Object> response = new HashMap<>();
        User user = new User(request.getName(), request.getLogin(), request.getPassword());
        userService.saveUser(user);
        if(user.getId() == null || user.getId() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_BLANK_FIELDS);
        }else{
            HashMap<String, Long> map = new HashMap<>();
            String sb = "UserId";
            map.put(sb, user.getId());
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, map);
        }
        return response;
    }

    @PutMapping(value = "/user")
    public HashMap<String, Object> updateUser(@RequestBody User request){
        HashMap<String, Object> response = new HashMap<>();
        User update = userService.findByLogin(request.getLogin());
        if(update.getLogin()!= null) {
            userService.saveUser(update);
            if(update.getId() != null){
                response.put(Constants.CODE, Response.SC_OK);
                response.put(Constants.RESPONSE, Constants.SUCCESS_UPDATED_USER);
            }else{
                response.put(Constants.CODE, Response.SC_NOT_ACCEPTABLE);
                response.put(Constants.RESPONSE, Constants.ERROR_UPDATE_USER);
            }
        }else{
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.RESPONSE, Constants.ERROR_NO_USER);
        }
        return response;
    }

    @DeleteMapping(value = "/user")
    public HashMap<String, Object> deleteUser(@RequestParam Long id){
        HashMap<String, Object> response = new HashMap<>();
        if(id == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_BLANK_FIELDS);
        }else{
            try {
                userService.deleteUser(id);
                response.put(Constants.CODE, Response.SC_OK);
                response.put(Constants.MESSAGE, Constants.SUCCESS_DELETED_USER);
            }catch (Exception ex){
                response.put(Constants.CODE, Response.SC_INTERNAL_SERVER_ERROR);
                response.put(Constants.MESSAGE, Constants.ERROR_DELETE_USER);
            }
        }
        return response;
    }

}
