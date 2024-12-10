package com.lab.service;

import com.lab.dto.PaperAddDTO;
import com.lab.dto.PaperQueryDTO;
import com.lab.entity.Paper;
import com.lab.result.PageResult;
import com.lab.vo.PaperVo;

public interface PaperService {
    /**
     * 论文分页查询接口
     * @param paperQueryDTO
     * @return
     */
    PageResult pageQuery(PaperQueryDTO paperQueryDTO);

    /**
     * 论文添加接口
     * @param paperAddDTO
     * @return
     */
    void paperAdd(PaperAddDTO paperAddDTO);

    /**
     * 论文详情获取接口
     * @param id
     * @return
     */
    PaperVo getById(Integer id);

    /**
     * 删除论文的接口
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /**
     * 论文更新接口
     * @param paperAddDTO
     * @return
     */
    void paperUpdate(Paper paper);
}
