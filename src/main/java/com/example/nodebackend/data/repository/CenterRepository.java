package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long>, CenterRepositoryCustom {
    @Query("select c from Center c where c.center_address LIKE %?1%")
    List<Center> findByCenterAddress(String region);
}
