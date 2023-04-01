package cryptoDOM.controller;

import cryptoDOM.configuration.jwt.Jwt;
import cryptoDOM.entity.User;
import cryptoDOM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private Jwt jwt;

    @GetMapping("/login")
    public String sendLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User userEntity, HttpServletResponse response) throws AuthenticationException {
        Optional<User> user = userService.getByUsernameAndPassword(userEntity);
        if (user.isPresent()) {
            final String token = jwt.generateToken(user.get().getUsername());
            final Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) Duration.ofHours(3).toSeconds());

            final Cookie cookieRole = new Cookie("myRole", user.get().getRole().getRole());
            response.addCookie(cookieRole);

            response.addCookie(cookie);
        }
        return "redirect:/api/showData";
    }
}
