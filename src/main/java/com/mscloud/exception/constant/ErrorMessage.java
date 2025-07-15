package com.mscloud.exception.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String MISSING_INPUT = "Incomplete input {0}";
    public static final String DUPLICATE_RECORD = "Already registered entry {0}";
}