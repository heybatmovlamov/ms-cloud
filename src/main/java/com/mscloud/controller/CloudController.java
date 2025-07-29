package com.mscloud.controller;

import com.mscloud.model.CloudDto;
import com.mscloud.model.PatientLisFileDto;
import com.mscloud.model.PatientLisResultDto;
import com.mscloud.service.CloudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{secretCode}/{qrCode}")
    public ResponseEntity<List<?>> getLisResultBySecretAndQrCode(@PathVariable String secretCode, @PathVariable String qrCode) {
        return ResponseEntity.ok(cloudService.getLisResultBySecretAndQrCode(secretCode, qrCode));
    }
    @GetMapping("/{secretCode}/{qrCode}/{reportUid}")
    public ResponseEntity<PatientLisFileDto> getLisFileByReportUidAndSecretAndQrCode(@PathVariable String secretCode, @PathVariable String qrCode, @PathVariable Integer reportUid) {
        return ResponseEntity.ok(cloudService.getLisFileByReportUidAndSecretAndQrCode(reportUid, secretCode, qrCode));
    }
}