package com.hepsiemlak.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.persistence.Id;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TodoList {
    @Id
    private String id;
    private String userId;
    private String name;
    private Instant createDate;
    private Instant updateDate;
}
