package com.hepsiemlak.todo.model.dto;


import com.hepsiemlak.todo.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;

    public User toUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setToken(UUID.randomUUID().toString());
        return user;
    }
}
