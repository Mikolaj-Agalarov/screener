package cryptoDOM.controller;


import cryptoDOM.dto.UserDTO;
import cryptoDOM.entity.User;
import cryptoDOM.mapper.UserMapper;
import cryptoDOM.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminPanelRestController {

    private final UserService userService;

    @GetMapping("/allusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers.stream()
                .map(UserMapper::fromUserToUserDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}