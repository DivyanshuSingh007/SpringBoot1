package com.example.demo.Repository;

//Just leave this like that the work of this interface is this much only
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
