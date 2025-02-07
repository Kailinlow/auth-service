package com.kltn.auth_service.ghn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GhnTokenResponse {
    private Integer code;
    private String message;
    private GhnTokenData data;
}