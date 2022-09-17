package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.model.dto.UserRequest;
import com.hepsiemlak.todo.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Map.entry;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public Map<String, String> auth(
            @RequestParam("email") final String email,
            @RequestParam("password") final String password
    ){
        String token= authService.login(email,password);
        if(StringUtils.isEmpty(token)){ throw new NotFoundException("Token not found!"); }
        return Map.ofEntries(
                entry("token", token)
        );
    }

    @PostMapping("/register")
    public void save(@RequestBody UserRequest userRequest){
        authService.register(userRequest);
    }
}
