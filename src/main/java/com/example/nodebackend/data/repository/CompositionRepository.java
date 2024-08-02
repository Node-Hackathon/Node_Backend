package com.example.nodebackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition,Long> {

    List<Composition> getByUserId(Long id);

}
