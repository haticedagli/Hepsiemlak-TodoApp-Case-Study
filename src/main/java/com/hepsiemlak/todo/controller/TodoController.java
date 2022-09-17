package com.hepsiemlak.todo.controller;

import com.hepsiemlak.todo.model.Todo;
import com.hepsiemlak.todo.model.dto.TodoRequest;
import com.hepsiemlak.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo-list")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/{todoListId}/todo")
    public List<Todo> getAll(@PathVariable String todoListId){
        return todoService.getTodos(todoListId);
    }

    @PostMapping("/{todoListId}/todo")
    public void save(@PathVariable String todoListId, @RequestBody TodoRequest todoRequest){
        todoService.saveTodo(todoListId, todoRequest);
    }

    @DeleteMapping("/todo/{id}")
    public void delete(@PathVariable String id){
        todoService.deleteTodoById(id);
    }

    @PutMapping("/todo/{id}")
    public Todo update(@PathVariable String id, @RequestBody TodoRequest todoRequest){
        return todoService.updateTodo(id, todoRequest);
    }

    @PostMapping("/todo/{id}/check")
    public void check(@PathVariable String id){
        todoService.checkTodo(id);
    }
}
