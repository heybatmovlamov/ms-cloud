package com.mscloud.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class PatientEntity {

    @Id
    private Long id;
}
