package com.globant.clientrest.transfer;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class ExceptionResponse {
    private Calendar timestamp;
    private String message;
    private String details;
}
