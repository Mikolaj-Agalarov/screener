package cryptoDOM.security;


import cryptoDOM.entity.Role;
import cryptoDOM.entity.User;
import cryptoDOM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUserEntity = userEntityService.getByUsername(username);
        if (optionalUserEntity.isPresent()) {
            User userEntity = userEntityService.getByUsername(username).get();
            return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
        } else {
            User newUser = new User();
            newUser.setUsername("new");
            Role role = new Role();
            role.setRole("new");
            newUser.setRole(role);
            return CustomUserDetails.fromUserEntityToCustomUserDetails(newUser);
        }

    }
}
