package cryptoDOM.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cryptoDOM.dto.UserDTO;

import cryptoDOM.entity.Role;
import cryptoDOM.entity.User;
import cryptoDOM.mapper.UserMapper;
import cryptoDOM.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminPanelRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = AdminPanelRestController.class)
public class AdminPanelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllUsers() throws Exception {
        // Create some test data
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser1");
        user1.setPassword("password1");
        user1.setRole(new Role("ROLE_ADMIN"));

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testuser2");
        user2.setPassword("password2");
        user1.setRole(new Role("ROLE_USER"));

        List<User> userList = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(userList);

        MvcResult result = mockMvc.perform(get("/admin/allusers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<UserDTO> userDTOList = mapper.readValue(responseJson, new TypeReference<List<UserDTO>>(){});

        assertThat(userDTOList).containsExactly(UserMapper.fromUserToUserDTO(user1), UserMapper.fromUserToUserDTO(user2));
    }
}