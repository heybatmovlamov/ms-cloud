package com.mscloud.controller;

import com.mscloud.model.CloudDto;
import com.mscloud.service.CloudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CloudController {

    private final CloudService cloudService;

    @PostMapping("/upload")
    public ResponseEntity<List<Integer>> upload(@RequestBody CloudDto cloudDto) {
        return ResponseEntity.ok(cloudService.upload(cloudDto));
    }

}
