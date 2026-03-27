package com.elektor.repository;

import com.elektor.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    public boolean existsByEmail(String email);
}
