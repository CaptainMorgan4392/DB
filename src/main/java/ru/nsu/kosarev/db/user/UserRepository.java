package ru.nsu.kosarev.db.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLoginAndPassword(String login, String password);

    @Override
    List<User> findAll();

}
