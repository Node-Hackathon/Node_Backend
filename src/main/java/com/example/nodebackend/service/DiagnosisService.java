package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.Diagnosis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DiagnosisService {
    List<Diagnosis> getAllDiagnoses(HttpServletRequest request);
    Diagnosis getDiagnosisById(Long id, HttpServletRequest request);
}
