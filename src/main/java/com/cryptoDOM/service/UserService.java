package com.cryptoDOM.service;

import com.cryptoDOM.configuration.PasswordConfig;
import com.cryptoDOM.entity.Role;
import com.cryptoDOM.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cryptoDOM.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordConfig passwordConfig;
    @Autowired
    private RoleService roleService;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        Role userRole = roleService.getByRole("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public Optional<User> getByUsernameAndPassword(User user) throws AuthenticationException {
        Optional<User> userEntity = userRepository.findByUsername(user.getUsername());
        if (userEntity.isPresent()) {
            if (passwordConfig.passwordEncoder().matches(user.getPassword(), userEntity.get().getPassword())) {
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

/*    public Optional<User> getByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }*/
}
