package com.lab.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    // 主键
    private long id;

    // 学号
    private String studentId;

    // 统一认证码
    private String authenticationId;

    // 姓名
    private String name;

    // 状态 0:禁用，1:启用
    private int status;

    // 手机号
    private String phone;

    //电子邮件
    private String email;

    // 性别
    private String sex;

    // 工作单位
    private String workPlace;

    // 职位：0：导师， 1：博士生 ，2：研究生， 3：本科生， 4：已就业
    private String type;

    // 入学时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollmentTime;

    // 毕业时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime graduationTime;

    // 身份证号
    private String idNumber;

    // 头像
    private String avatar;

}
