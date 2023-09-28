package servlets;

import models.User;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T entity);
    List<T> findAll();
    Optional<T> findByLogin(T login);

    boolean findUser(String username, String password);

    boolean findUserByEmail(String email);
}