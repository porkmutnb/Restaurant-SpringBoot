package com.chermew.restaurant.model;

import lombok.Data;

@Data
public class ResponsePayload {
    private Integer code;
    private String message;
    private Object data;
}
