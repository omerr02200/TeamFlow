package com.works.repositories;

import com.works.entities.Role;
import com.works.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByFirstNameContainsAndLastNameContainsAllIgnoreCase(String firstName, String lastName, Pageable pageable);

    Optional<User> findByEmailEqualsIgnoreCase(String email);

    Page<User> findByFirstNameContainsOrLastNameContainsOrEmailContainsAllIgnoreCase
            (String firstName, String lastName, String email, Pageable pageable);
}