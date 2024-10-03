package com.bulyanc.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.bulyanc.backend.entity.User;
import com.bulyanc.backend.repository.UserRepository;

class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    public void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAll();
        assertEquals(0, users.size());
    }
}