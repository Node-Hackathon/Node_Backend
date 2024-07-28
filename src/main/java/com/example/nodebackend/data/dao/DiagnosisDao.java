package com.example.nodebackend.data.dao;

import com.example.nodebackend.data.entity.Diagnosis;

import java.util.List;

public interface DiagnosisDao {
    List<Diagnosis> getAllDiagnoses();
}
