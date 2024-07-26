package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.CenterDao;
import com.example.nodebackend.data.entity.Center;
import com.example.nodebackend.data.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CenterDaoImpl implements CenterDao {

    private final CenterRepository centerRepository;

    @Autowired
    public CenterDaoImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    public List<Center> findCentersByRegion(String region) {
        return centerRepository.findByCenterAddress(region);
    }
}
