package com.dairyservice.repository;

import com.dairyservice.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    Optional<Session> findByUserId(Long userId);
    void deleteByToken(String token);
    void deleteByUserId(Long userId);
}
