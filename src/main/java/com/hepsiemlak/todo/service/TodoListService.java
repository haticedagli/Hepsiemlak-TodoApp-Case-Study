package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.model.TodoList;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.TodoListRequest;
import com.hepsiemlak.todo.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private AuthService authService;

    public List<TodoList> getTodoLists(){
        User user = authService.getAuthenticatedUser();
        return todoListRepository.findByUserId(user.getId());
    }

    public TodoList getTodoListById(String id){
        User user = authService.getAuthenticatedUser();
        return todoListRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("Todo list not found!"));
    }

    public void saveTodoList(TodoListRequest todoListRequest){
        User user = authService.getAuthenticatedUser();
        TodoList todoList = todoListRequest.toTodoList(user.getId());
        todoListRepository.save(todoList);
    }

    public void deleteTodoList(String id){
        TodoList todoList = getTodoListById(id);
        todoListRepository.delete(todoList);
    }

    public TodoList updateTodoList(String id, TodoListRequest todoListRequest){
        User user = authService.getAuthenticatedUser();
        TodoList existTodoList = getTodoListById(id);
        TodoList todoList = todoListRequest.toTodoList(user.getId());
        todoList.setId(existTodoList.getId());
        todoList.setCreateDate(existTodoList.getCreateDate());
        todoList.setUpdateDate(Instant.now());
        return todoListRepository.save(todoList);
    }
}
