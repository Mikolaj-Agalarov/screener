package com.cryptoDOM.service;
import com.cryptoDOM.entity.Role;
import com.cryptoDOM.entity.User;
import com.cryptoDOM.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void createSample() {
        List<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User(1L, "User1", "q123z", "User1@gmail.com", new Role(), new Date()));
        sampleUsers.add(new User(2L, "User2", "q123z", "User2@gmail.com", new Role(), new Date()));

        when(userRepository.findAll()).thenReturn(sampleUsers);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userEntities = userService.getAllUsers();

        assertEquals(2, userEntities.size());
        assertEquals("User1", userEntities.get(0).getUsername());
        assertEquals("User1@gmail.com", userEntities.get(0).getEmail());
        assertEquals("User2", userEntities.get(1).getUsername());
        assertEquals("User2@gmail.com", userEntities.get(1).getEmail());
    }
}
