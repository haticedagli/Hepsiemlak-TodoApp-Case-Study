package com.hepsiemlak.todo.model.dto;

import com.hepsiemlak.todo.model.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class TodoRequest {
    private String todo;
    public Todo toTodo(String todoListId, String userId){
        Todo todoModel = new Todo();
        todoModel.setId(UUID.randomUUID().toString());
        todoModel.setTodo(todo);
        todoModel.setTodoListId(todoListId);
        todoModel.setUserId(userId);
        todoModel.setCheck(false);
        todoModel.setCreateDate(Instant.now());
        return todoModel;
    }
}
