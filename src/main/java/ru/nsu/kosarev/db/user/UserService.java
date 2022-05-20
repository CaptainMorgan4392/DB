package ru.nsu.kosarev.db.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO auth(UserDTO userDTO) {
        Optional<User> user = userRepository.findByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());

        if (user.isPresent()) {
            User foundUser = user.get();

            return new UserDTO(
                foundUser.getLogin(),
                foundUser.getPassword(),
                foundUser.getUserRole()
            );
        }

        return null;
    }

    public UserDTO register(UserDTO userDTO) {
        Optional<User> user = userRepository.findByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());

        if (user.isEmpty()) {
            User foundUser = userRepository.save(
                new User(
                    null,
                    userDTO.getLogin(),
                    userDTO.getPassword(),
                    userDTO.getUserRole()
                )
            );

            return new UserDTO(
                foundUser.getLogin(),
                foundUser.getPassword(),
                foundUser.getUserRole()
            );
        }

        return null;
    }

    private UserDTO getUserDTOInConcreteCase(UserDTO userDTO, boolean isAuthorization) {
        Optional<User> user = userRepository.findByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());

        if (isAuthorization == user.isPresent()) {
            User foundUser = user.get();

            return new UserDTO(
                foundUser.getLogin(),
                foundUser.getPassword(),
                foundUser.getUserRole()
            );
        }

        return null;
    }

    public List<UserDTO> fetch() {
        return userRepository.findAll().stream().map(user ->
            new UserDTO(user.getLogin(), user.getPassword(), user.getUserRole())
        ).collect(Collectors.toList());
    }

    public void changeRole(@PathVariable("id") Integer userId, UserRole userRole) {
        userRepository.findById(userId).ifPresent(it -> {
            it.setUserRole(userRole);
            userRepository.save(it);
        });
    }

}
