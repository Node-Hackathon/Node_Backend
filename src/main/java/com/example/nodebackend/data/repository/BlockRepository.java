package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Block;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block,Long> {
    Optional<Block> findByIdAndUserId(Long id, Long userId);
}
