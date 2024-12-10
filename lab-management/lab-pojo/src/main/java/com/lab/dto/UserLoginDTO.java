package com.lab.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String studentId;
    private String password;
    private String uuid;
    private String code;
    private Boolean isVery;
}
