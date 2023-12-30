package com.example.paymentsapi.repository.User;

import com.example.paymentsapi.web.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserId(String email);

    boolean existsByuserId(String userId);


    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
