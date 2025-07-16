package com.mscloud.model;

import java.util.LinkedList;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CloudDto {

    @Builder.Default
    private LinkedList<LocalDto> cloudResponseList = new LinkedList<>();
}
