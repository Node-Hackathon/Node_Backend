package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.Center;
import com.example.nodebackend.data.repository.CenterRepository;
import com.example.nodebackend.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    @Autowired
    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    public List<Center> getAllCenters( HttpServletRequest request) {
        return centerRepository.findAll();

    }

    @Override
    public List<Center> findCentersByRegion(String region, HttpServletRequest request) {
        return centerRepository.findByCenterAddress(region);
    }
}
