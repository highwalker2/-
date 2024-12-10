package com.lab.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String studentId;
    private String password;
    private String repassword;
    private String uuid;
    private String code;
}
