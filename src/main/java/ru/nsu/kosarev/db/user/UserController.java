package ru.nsu.kosarev.db.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth")
    public UserDTO auth(@RequestBody UserDTO userDTO) {
        return userService.auth(userDTO);
    }

    @PostMapping(value = "/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping(value = "/fetch")
    public List<UserDTO> getListOfUsers() {
        return userService.fetch();
    }

    @PostMapping(value = "/change/{id}/{userRole}")
    public void changeRole(@PathVariable("id") Integer userId, @PathVariable("userRole") String userRole) {
        userService.changeRole(userId, UserRole.valueOf(userRole));
    }

}
