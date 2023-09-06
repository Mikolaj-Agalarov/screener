package com.cryptoDOM.controller;

import com.cryptoDOM.dto.UserDTO;
import com.cryptoDOM.entity.User;
import com.cryptoDOM.service.UserService;
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


@ExtendWith(MockitoExtension.class)
public class AdminPanelRestControllerTest {
    @InjectMocks
    private AdminPanelRestController adminPanelRestController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void createSample() {
        List<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User(1L, "User1", "q123z", "User1@gmail.com", null,
                new Date()));
        sampleUsers.add(new User(2L, "User2", "q123z", "User2@gmail.com", null,
                new Date()));

        Mockito.when(userService.getAllUsers()).thenReturn(sampleUsers);
    }

    @Test
    public void testGetAllUsers() {
        List<UserDTO> userDTOList = adminPanelRestController.getAllUsers().getBody();

        assertEquals(2, userDTOList.size());
        assertEquals("User1", userDTOList.get(0).getUsername());
        assertEquals("User1@gmail.com", userDTOList.get(0).getEmail());
        assertEquals("User2", userDTOList.get(1).getUsername());
        assertEquals("User2@gmail.com", userDTOList.get(1).getEmail());

        if (userDTOList.size() == 2) {
            System.out.println("Test passed: All users retrieved successfully.");
        }
    }
}
