package com.example.nodebackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block,Long> {
    Optional<Block> findByIdAndUserId(Long id, Long userId);
    List<Block> getByUserId(Long id);
}
