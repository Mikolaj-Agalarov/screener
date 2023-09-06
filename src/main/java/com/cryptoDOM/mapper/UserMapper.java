package com.cryptoDOM.mapper;

import com.cryptoDOM.dto.UserDTO;
import com.cryptoDOM.entity.User;

public class UserMapper {
    public static User fromUserDTOtoUser (UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static UserDTO fromUserToUserDTO (User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(userDTO.getRole());
        return userDTO;
    }
}
