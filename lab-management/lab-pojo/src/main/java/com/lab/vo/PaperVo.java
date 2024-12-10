package com.lab.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperVo {

    // 论文ID
    private Integer id;

    // 论文标题
    private String title;

    // 关键词
    private String keyword;

    // 摘要
    private String abstractText;

    // 所有作者姓名
    private String authorAll;

    // 发表期刊名称
    private String publishName;

    //论文领域: 0 社交网络； 1 VLC； 2 交通 (可设置为空，代表其他类型的论文）
    private String field;

    // 论文类型：0 实验室论文；1 必看论文；2 推荐论文
    private String type;

    // 论文链接
    private String urlLink;

    // 提交时间
    private Date submitTime;

    // 接收时间
    private Date acceptanceTime;

    // 下载链接
    private String downloadLink;

    // 引用次数
    private Integer citedNumber;
}
