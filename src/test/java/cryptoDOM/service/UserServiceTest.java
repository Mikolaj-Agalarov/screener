package cryptoDOM.service;


import cryptoDOM.configuration.PasswordConfig;
import cryptoDOM.entity.Role;
import cryptoDOM.entity.User;
import cryptoDOM.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserService.class)
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private PasswordConfig passwordConfig;


    @Test
    void testSaveUser() {
        User user = new User();
        Role role = new Role();
        role.setRole("ROLE_USER");
        user.setUsername("new");
        user.setPassword("user");
        user.setEmail("newuser@gmail.com");

        Mockito.when(userRepository.getUserByUsername(user.getUsername()))
                .thenReturn(Optional.empty());

        Mockito.when(roleService.getByRole("ROLE_USER"))
                .thenReturn(role);

        Mockito.when(passwordConfig.passwordEncoder().encode(user.getPassword()))
                .thenReturn("encodedPassword");

        boolean result = userService.saveUser(user);

        Assertions.assertTrue(result);
        Assertions.assertEquals(user.getRole(), role);
        Assertions.assertEquals(user.getPassword(), "encodedPassword");

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        // В рамках тестирования такое делать можно? (удалять тестового юзера, которого добавляем)
        userService.deleteUser(user.getUsername());
    }

    @Test
    void testSaveUserWithExistingUsername() {
        User user = new User();
        user.setUsername("belaz");
        user.setPassword("belaz1");
        user.setEmail("belazproger@gmail.com");

        Mockito.when(userRepository.getUserByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        boolean result = userService.saveUser(user);

        Assertions.assertFalse(result);

        Mockito.verify(userRepository, Mockito.never()).save(user);
    }
}

