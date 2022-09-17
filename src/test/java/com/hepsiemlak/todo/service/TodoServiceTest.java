package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.model.Todo;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.TodoRequest;
import com.hepsiemlak.todo.repository.TodoListRepository;
import com.hepsiemlak.todo.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    private AuthService authService;

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    public void getTodos_should_throw_exception_if_todo_list_is_not_found_for_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoListId = "1";

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoListRepository.existsByIdAndUserId(todoListId, user.getId()))
                .willReturn(false);

        //when
        Throwable throwable = catchThrowable(() -> todoService.getTodos(todoListId));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Todo list not found!");
    }

    @Test
    public void getTodos_should_return_todos_for_given_todo_list_for_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoListId = "1";

        Todo todo = new Todo();
        todo.setId("123");
        todo.setTodo("Yemek yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoListRepository.existsByIdAndUserId(todoListId, user.getId()))
                .willReturn(true);

        BDDMockito
                .given(todoRepository.findByTodoListIdAndUserId(todoListId, user.getId()))
                .willReturn(List.of(todo));

        //when
        List<Todo> todos = todoService.getTodos(todoListId);

        //then
        assertThat(todos.size()).isEqualTo(1);
        assertThat(todos.get(0)).isEqualTo(todo);
    }

    @Test
    public void saveTodo_should_throw_exception_if_todo_list_is_not_found_for_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoListId = "1";

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTodo("Yemek Yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoListRepository.existsByIdAndUserId(todoListId, user.getId()))
                .willReturn(false);

        //when
        Throwable throwable = catchThrowable(() -> todoService.saveTodo(todoListId, todoRequest));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Todo list not found!");
    }

    @Test
    public void saveTodo_should_save_todo_with_correct_mapping_for_given_todo_list_for_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoListId = "1";

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTodo("Yemek Yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoListRepository.existsByIdAndUserId(todoListId, user.getId()))
                .willReturn(true);

        //when
        todoService.saveTodo(todoListId, todoRequest);

        //then
        ArgumentCaptor<Todo> savedCaptor = ArgumentCaptor.forClass(Todo.class);
        Mockito
                .verify(todoRepository, times(1))
                .save(savedCaptor.capture());

        assertThat(savedCaptor.getValue().getTodo()).isEqualTo(todoRequest.getTodo());
    }

    @Test
    public void deleteTodoById_should_throw_exception_if_todo_not_found() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> todoService.deleteTodoById(todoId));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Todo not found!");
    }

    @Test
    public void deleteTodoById_should_throw_exception_if_founded_todo_not_belong_to_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("1234");
        todo.setTodo("Yemek yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        Throwable throwable = catchThrowable(() -> todoService.deleteTodoById(todoId));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("This todo is not belong to this user.");
    }

    @Test
    public void deleteTodoById_should_delete_todo_if_founded_todo_is_belong_to_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("123");
        todo.setTodo("Yemek yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        todoService.deleteTodoById(todoId);

        //then
        ArgumentCaptor<Todo> savedCaptor = ArgumentCaptor.forClass(Todo.class);
        Mockito
                .verify(todoRepository, times(1))
                .delete(savedCaptor.capture());

        assertThat(savedCaptor.getValue()).isEqualTo(todo);
    }

    @Test
    public void updateTodo_should_throw_exception_if_todo_not_found() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTodo("Yemek Yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> todoService.updateTodo(todoId, todoRequest));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Todo not found!");
    }

    @Test
    public void updateTodo_should_throw_exception_if_founded_todo_not_belong_to_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("1234");
        todo.setTodo("Yemek yap");

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTodo("Yemek Yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        Throwable throwable = catchThrowable(() -> todoService.updateTodo(todoId, todoRequest));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("This todo is not belong to this user.");
    }

    @Test
    public void updateTodo_should_save_todo_with_correct_mapping() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("123");
        todo.setTodo("Yemek yap");

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTodo("Yemek Yap 2");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        todoService.updateTodo(todoId, todoRequest);

        //then
        ArgumentCaptor<Todo> savedCaptor = ArgumentCaptor.forClass(Todo.class);
        Mockito
                .verify(todoRepository, times(1))
                .save(savedCaptor.capture());

        assertThat(savedCaptor.getValue().getTodo()).isEqualTo(todoRequest.getTodo());
    }

    @Test
    public void checkTodo_should_throw_exception_if_todo_not_found() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> todoService.checkTodo(todoId));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Todo not found!");
    }

    @Test
    public void checkTodo_should_throw_exception_if_founded_todo_not_belong_to_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("1234");
        todo.setTodo("Yemek yap");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        Throwable throwable = catchThrowable(() -> todoService.checkTodo(todoId));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("This todo is not belong to this user.");
    }

    @Test
    public void checkTodo_should_save_todo_with_correct_mapping() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        String todoId = "1";

        Todo todo = new Todo();
        todo.setId("1");
        todo.setUserId("123");
        todo.setTodo("Yemek yap");
        todo.setCheck(false);

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(todoRepository.findById(todoId))
                .willReturn(Optional.of(todo));

        //when
        todoService.checkTodo(todoId);

        //then
        ArgumentCaptor<Todo> savedCaptor = ArgumentCaptor.forClass(Todo.class);
        Mockito
                .verify(todoRepository, times(1))
                .save(savedCaptor.capture());

        assertThat(savedCaptor.getValue().getCheck()).isEqualTo(true);
    }
}