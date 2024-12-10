package com.lab.dto;

import lombok.Data;

@Data
public class UserInfoUpdateDTO {
    private int id;
    private String studentId;
    private String name;
    private String email;
    private String phone;
}
