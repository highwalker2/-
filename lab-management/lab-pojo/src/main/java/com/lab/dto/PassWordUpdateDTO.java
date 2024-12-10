package com.lab.dto;

import lombok.Data;

@Data
public class PassWordUpdateDTO {
    private String old_pwd;
    private String new_pwd;
    private String re_pwd;
}
