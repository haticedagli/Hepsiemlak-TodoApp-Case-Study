package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.BusinessException;
import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.UserRequest;
import com.hepsiemlak.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public User getUser(){
        return authService.getAuthenticatedUser();
    }

    public void deleteUser(){
        User user = authService.getAuthenticatedUser();
        userRepository.delete(user);
    }

    public User updateUser(UserRequest userRequest){
        User existUser = authService.getAuthenticatedUser();
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException("Email already exists!");
        }
        User user = userRequest.toUser();
        user.setId(existUser.getId());
        user.setToken(existUser.getToken());
        user.setCreateDate(existUser.getCreateDate());
        user.setUpdateDate(Instant.now());
        return userRepository.save(user);
    }
}
