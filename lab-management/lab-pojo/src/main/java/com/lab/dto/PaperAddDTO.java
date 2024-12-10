package com.lab.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PaperAddDTO {
    // 名称
    private String name;

    // 关键词
    private String keyword;

    // 摘要文本
    private String abstractText;

    // 所有作者
    private String authorAll;

    // 出版名称
    private String publishName;

    // 提交时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitTime;

    // 接收时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acceptanceTime;

    // 实时获取的领域类型
    private String fieldValue;

    // 类型值
    private String typeValue;

    // 引用次数
    private int num;

    // URL链接
    private String urlLink;

    //下载链接
    private String downloadLink;
}
