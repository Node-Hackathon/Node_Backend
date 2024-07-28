package com.example.nodebackend.data.dao;

import com.example.nodebackend.data.entity.Center;
import java.util.List;

public interface CenterDao {
    List<Center> findCentersByRegion(String region);
}
