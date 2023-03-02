package com.example.pytest.Auth;

import com.example.pytest.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByAccount(String account);

    Optional<User> findByEmail(String email);
//    Optional<User> findUserByEmail(String email);
}
