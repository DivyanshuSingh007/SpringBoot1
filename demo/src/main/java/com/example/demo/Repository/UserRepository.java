package com.example.demo.Repository;

//Just leave this like that the work of this interface is this much only
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
