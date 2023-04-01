package cryptoDOM.service;


import cryptoDOM.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


//@ContextConfiguration(classes = {PasswordConfig.class, SecurityConfig.class} )
@EnableConfigurationProperties
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("belaz");
        user.setPassword("belaz1");
        user.setEmail("belazproger@gmail.com");

        Assert.assertFalse(userService.saveUser(user));
    }
}