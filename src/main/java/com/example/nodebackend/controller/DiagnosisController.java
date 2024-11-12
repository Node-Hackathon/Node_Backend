package com.example.nodebackend.controller;

import com.example.nodebackend.data.entity.Diagnosis;
import com.example.nodebackend.service.DiagnosisService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/diagnosis-api")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @Autowired
    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @GetMapping
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<Diagnosis>> getAllDiagnoses(HttpServletRequest request) {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnoses(request);
        return ResponseEntity.ok(diagnoses);
    }

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable Long id, HttpServletRequest request) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id, request);
        return ResponseEntity.ok(diagnosis);
    }
}
