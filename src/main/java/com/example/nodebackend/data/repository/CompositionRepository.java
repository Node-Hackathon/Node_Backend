package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.data.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition,Long> {

    List<Composition> getByUserId(Long id);

}
