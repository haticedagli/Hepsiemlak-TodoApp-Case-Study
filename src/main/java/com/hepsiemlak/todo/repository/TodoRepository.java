package com.hepsiemlak.todo.repository;

import com.hepsiemlak.todo.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo, String> {
    List<Todo> findByTodoListIdAndUserId(String todoListId, String userId);
}
