package com.hepsiemlak.todo.model.dto;

import com.hepsiemlak.todo.model.TodoList;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class TodoListRequest {
    private String name;

    public TodoList toTodoList(String userId){
        TodoList todoList = new TodoList();
        todoList.setId(UUID.randomUUID().toString());
        todoList.setUserId(userId);
        todoList.setName(name);
        todoList.setCreateDate(Instant.now());
        return todoList;
    }
}
