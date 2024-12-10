package com.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PaperQueryDTO implements Serializable {
    //请求页数
    private int page;

    //每页最大值
    private int pageSize;

    // 论文标题
    private String title;

    // 所有作者姓名
    private String authorAll;

    //论文领域: 0 社交网络； 1 VLC； 2 交通 (可设置为空，代表其他类型的论文）
    private String field;

    // 论文类型：0 实验室论文；1 必看论文；2 推荐论文
    private String type;

    // 接收时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
