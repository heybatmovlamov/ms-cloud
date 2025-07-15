package com.mscloud.model;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientsIdDto {

    private Set<Integer> id;
}
