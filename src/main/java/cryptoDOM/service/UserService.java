package cryptoDOM.service;

import cryptoDOM.configuration.PasswordConfig;
import cryptoDOM.dto.UserDTO;
import cryptoDOM.entity.Role;
import cryptoDOM.entity.User;
import cryptoDOM.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cryptoDOM.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleService roleService;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        Optional<User> isUsernameFree = userRepository.getUserByUsername(user.getUsername());
        if (isUsernameFree.isPresent()) {
            return false;
        } else {
            Role userRole = roleService.getByRole("ROLE_USER");
            user.setRole(userRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
    }

    public Optional<User> getByUsernameAndPassword(User user) throws AuthenticationException {
        Optional<User> userEntity = userRepository.findByUsername(user.getUsername());
        if (userEntity.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), userEntity.get().getPassword())) {
                return userEntity;
            } else {
                throw new UsernameNotFoundException(user.getUsername());
            }
        } else {
            throw new UsernameNotFoundException(user.getUsername());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

/*    public Optional<User> getByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }*/
}
