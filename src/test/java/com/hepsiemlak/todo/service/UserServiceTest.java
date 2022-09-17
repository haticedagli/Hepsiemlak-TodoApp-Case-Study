package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.UserRequest;
import com.hepsiemlak.todo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUser_should_return_authenticated_user() {
        //given
        User user = new User();
        user.setEmail("haticeetoglu03@gmail.com");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        //when
        User result = userService.getUser();

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void deleteUser_should_remove_authenticated_user() {
        //given
        User user = new User();
        user.setEmail("haticeetoglu03@gmail.com");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        //when
        userService.deleteUser();

        //then
        ArgumentCaptor<User> savedCaptor = ArgumentCaptor.forClass(User.class);
        Mockito
                .verify(userRepository, times(1))
                .delete(savedCaptor.capture());
        assertThat(savedCaptor.getValue()).isEqualTo(user);
    }

    @Test
    public void updateUser_should_throw_exception_if_email_already_exists() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("hatice@gmail.com");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(userRepository.existsByEmail(userRequest.getEmail()))
                .willReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userRequest));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable.getMessage()).isEqualTo("Email already exists!");
    }

    @Test
    public void updateUser_should_update_authenticated_user() {
        //given
        User user = new User();
        user.setId("123");
        user.setEmail("haticeetoglu03@gmail.com");

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("hatice@gmail.com");

        BDDMockito
                .given(authService.getAuthenticatedUser())
                .willReturn(user);

        BDDMockito
                .given(userRepository.existsByEmail(userRequest.getEmail()))
                .willReturn(false);

        //when
        userService.updateUser(userRequest);

        //then
        ArgumentCaptor<User> savedCaptor = ArgumentCaptor.forClass(User.class);
        Mockito
                .verify(userRepository, times(1))
                .save(savedCaptor.capture());

        assertThat(savedCaptor.getValue().getId()).isEqualTo(user.getId());
        assertThat(savedCaptor.getValue().getEmail()).isEqualTo(userRequest.getEmail());
    }
}