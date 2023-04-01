package cryptoDOM.service;

import cryptoDOM.configuration.PasswordConfig;
import cryptoDOM.configuration.SecurityConfig;
import cryptoDOM.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


//@ContextConfiguration(classes = {PasswordConfig.class, SecurityConfig.class} )
@EnableConfigurationProperties
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
class UserServiceTest {
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