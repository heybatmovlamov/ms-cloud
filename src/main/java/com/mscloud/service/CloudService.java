package com.mscloud.service;

import com.mscloud.dao.repository.CloudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudService {

    private final CloudRepository repository;


}
