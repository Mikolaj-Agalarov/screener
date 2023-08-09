package cryptoDOM.service;

import cryptoDOM.entity.Role;
import cryptoDOM.entity.User;
import cryptoDOM.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void testSaveUser() {
        User user = new User();
        Role role = new Role("ROLE_USER");
        user.setUsername("new");
        user.setPassword("user");
        user.setEmail("newuser@gmail.com");

        Mockito.when(userRepository.getUserByUsername(user.getUsername()))
                .thenReturn(Optional.empty());

        Mockito.when(roleService.getByRole("ROLE_USER"))
                .thenReturn(role);

        Mockito.when(passwordEncoder.encode(user.getPassword()))
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
