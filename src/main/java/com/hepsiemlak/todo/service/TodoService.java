package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.BusinessException;
import com.hepsiemlak.todo.exception.NotFoundException;
import com.hepsiemlak.todo.model.Todo;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.TodoRequest;
import com.hepsiemlak.todo.repository.TodoListRepository;
import com.hepsiemlak.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private AuthService authService;

    public List<Todo> getTodos(String todoListId){
        User user = authService.getAuthenticatedUser();
        if(!todoListRepository.existsByIdAndUserId(todoListId, user.getId())){
            throw new NotFoundException("Todo list not found!");
        }
        return todoRepository.findByTodoListIdAndUserId(todoListId, user.getId());
    }

    public void saveTodo(String todoListId, TodoRequest todoRequest){
        User user = authService.getAuthenticatedUser();
        if(!todoListRepository.existsByIdAndUserId(todoListId, user.getId())){
            throw new NotFoundException("Todo list not found!");
        }
        Todo todo = todoRequest.toTodo(todoListId, user.getId());
        todoRepository.save(todo);
    }

    public void deleteTodoById(String id){
        User user = authService.getAuthenticatedUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found!"));
        if(!todo.getUserId().equals(user.getId())) {
            throw new BusinessException("This todo is not belong to this user.");
        }
        todoRepository.delete(todo);
    }

    public Todo updateTodo(String id, TodoRequest todoRequest){
        User user = authService.getAuthenticatedUser();
        Todo existTodo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found!"));
        if(!existTodo.getUserId().equals(user.getId())) {
            throw new BusinessException("This todo is not belong to this user.");
        }
        Todo todo = todoRequest.toTodo(existTodo.getTodoListId(), user.getId());
        todo.setId(existTodo.getId());
        todo.setCreateDate(existTodo.getCreateDate());
        todo.setUpdateDate(Instant.now());
        return todoRepository.save(todo);
    }

    public void checkTodo(String id){
        User user = authService.getAuthenticatedUser();
        Todo existTodo = todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found!"));
        if(!existTodo.getUserId().equals(user.getId())) {
            throw new BusinessException("This todo is not belong to this user.");
        }
        existTodo.setCheck(!existTodo.getCheck());
        existTodo.setUpdateDate(Instant.now());
        todoRepository.save(existTodo);
    }
}
