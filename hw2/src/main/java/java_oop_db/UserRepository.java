package java_oop_db;

import java.util.List;

public interface UserRepository<T> {
    List<T> findAllByAge(Integer age);
    List<T> findByUsername(String username);
}

