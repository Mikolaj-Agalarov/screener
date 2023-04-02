package cryptoDOM.controller;

import cryptoDOM.dto.UserDTO;
import cryptoDOM.entity.User;
import cryptoDOM.mapper.UserMapper;
import cryptoDOM.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;


@EnableConfigurationProperties
@SpringBootTest
public class AdminPanelRestControllerTest {

    @Autowired
    private AdminPanelRestController adminPanelRestController;


    @Autowired
    private UserService userService;

    @Test
    void getAllUsersTest() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTO = users.stream().map(UserMapper::fromUserToUserDTO).collect(Collectors.toList());

        List<UserDTO> usersFromRestApi = adminPanelRestController.getAllUsers().getBody();
        Assert.assertEquals(usersDTO, usersFromRestApi);

    }
}