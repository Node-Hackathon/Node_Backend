package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.Center;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CenterService {
    List<Center> getAllCenters( HttpServletRequest request);
    List<Center> findCentersByRegion(String region, HttpServletRequest request);
}
