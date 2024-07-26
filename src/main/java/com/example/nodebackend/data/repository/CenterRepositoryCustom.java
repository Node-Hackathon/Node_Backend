package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Center;

import java.util.List;

public interface CenterRepositoryCustom {
    List<Center> getCenterList(String region);
}
