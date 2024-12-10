package com.lab.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeCountVo {
    //导师
    private Integer mentor;
    //博士生
    private Integer doctor;
    //研究生
    private Integer postgraduate;
    //本科生
    private Integer undergraduate;
    //已就业
    private Integer employee;
}
