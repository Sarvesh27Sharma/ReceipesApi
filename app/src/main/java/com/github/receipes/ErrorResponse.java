package com.github.receipes;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String code;
    String message;
}
