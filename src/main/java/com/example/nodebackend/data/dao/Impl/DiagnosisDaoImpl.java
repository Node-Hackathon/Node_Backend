package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.DiagnosisDao;
import com.example.nodebackend.data.entity.Diagnosis;
import com.example.nodebackend.data.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiagnosisDaoImpl implements DiagnosisDao {

    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisDaoImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }
}
