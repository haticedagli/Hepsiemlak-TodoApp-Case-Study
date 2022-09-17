package com.hepsiemlak.todo.repository;

import com.hepsiemlak.todo.model.TodoList;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends CouchbaseRepository<TodoList, String> {
    List<TodoList> findByUserId(String userId);
    Optional<TodoList> findByIdAndUserId(String id, String userId);
    void deleteByIdAndUserId(String id, String userId);
    boolean existsByIdAndUserId(String id, String userId);
}
