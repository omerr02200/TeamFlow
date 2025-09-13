package com.works.repositories;

import com.works.entities.TeamUser;
import com.works.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

    Optional<TeamUser> findByUser_UidEquals(Long uid);

    Page<TeamUser> findByUserEquals(User user, Pageable pageable);
}