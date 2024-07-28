package com.example.nodebackend.service.impl;

import com.example.nodebackend.data.entity.Diagnosis;
import com.example.nodebackend.data.repository.DiagnosisRepository;
import com.example.nodebackend.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public List<Diagnosis> getAllDiagnoses(HttpServletRequest request) {
        return diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis getDiagnosisById(Long id, HttpServletRequest request) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(id);
        return diagnosis.orElse(null);
    }
}
