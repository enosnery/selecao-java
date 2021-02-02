package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.UserService;
import com.enosnery.RestAPI.utils.Constants;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user/{userId}")
    public HashMap<String, Object> getUser(@PathVariable("userId") Long userId){
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

    @GetMapping(value = "/user")
    public HashMap<String, Object> getAllUsers(){
        HashMap<String, Object> response = new HashMap<>();
        List<User> userList = userService.findAll();
        response.put(Constants.CODE, Response.SC_OK);
        response.put(Constants.RESPONSE, userList);
        return response;
    }


    @PostMapping(value = "/user")
    public HashMap<String, Object> insertUser(@RequestBody User request){
        HashMap<String, Object> response = new HashMap<>();
        User newUser = new User(request.getName(), request.getLogin());
        User user = userService.saveUser(newUser);
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
        if(request.getLogin()!= null) {
            User update = userService.updateUser(request);
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

    @DeleteMapping(value = "/user/{id}")
    public HashMap<String, Object> deleteUser(@PathVariable("id") Long id){
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
