package com.bulyanc.backend.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bulyanc.backend.entity.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll() {
        Iterable<User> users = userRepository.findAll();
        assertFalse(users.iterator().hasNext());
    }
}
