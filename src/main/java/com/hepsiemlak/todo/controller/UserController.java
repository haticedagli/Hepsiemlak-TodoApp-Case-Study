package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.UserRequest;
import com.hepsiemlak.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User get(){
        return userService.getUser();
    }

    @PutMapping
    public User update(@RequestBody UserRequest userRequest){
        return userService.updateUser(userRequest);
    }

    @DeleteMapping
    public void delete(){
        userService.deleteUser();
    }
}
