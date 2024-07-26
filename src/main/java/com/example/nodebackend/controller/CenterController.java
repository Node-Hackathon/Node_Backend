package com.example.nodebackend.controller;

import com.example.nodebackend.data.entity.Center;
import com.example.nodebackend.service.CenterService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/center-api")

public class CenterController {

    private final CenterService centerService;
    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping
    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
    public ResponseEntity<List<Center>> getAllCenters( HttpServletRequest request) {
        List<Center> centers = centerService.getAllCenters(request);
        return ResponseEntity.ok(centers);
    }

    @GetMapping("/search")
    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
    public ResponseEntity<List<Center>> findCentersByRegion(@RequestParam String region, HttpServletRequest request) {
        List<Center> centers = centerService.findCentersByRegion(region, request);
        return ResponseEntity.ok(centers);
    }

}
