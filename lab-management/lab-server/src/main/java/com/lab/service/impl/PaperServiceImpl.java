package com.lab.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lab.dto.PaperAddDTO;
import com.lab.dto.PaperQueryDTO;
import com.lab.entity.Paper;
import com.lab.mapper.PaperMapper;
import com.lab.result.PageResult;
import com.lab.result.Result;
import com.lab.service.PaperService;
import com.lab.vo.PaperVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Slf4j
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;
    /**
     * 论文分页查询接口
     * @param paperQueryDTO
     * @return
     */
    public PageResult pageQuery(PaperQueryDTO paperQueryDTO) {
        PageHelper.startPage(paperQueryDTO.getPage(),paperQueryDTO.getPageSize());
        Page<PaperVo> page=paperMapper.pageQuery(paperQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 论文添加接口
     * @param paperAddDTO
     * @return
     */
    public void paperAdd(PaperAddDTO paperAddDTO) {
        paperMapper.paperAdd(paperAddDTO);
    }

    /**
     * 论文详情获取接口
     * @param id
     * @return
     */
    public PaperVo getById(Integer id) {
        PaperVo paperVo=paperMapper.getById(id);
        return paperVo;
    }

    /**
     * 删除论文的接口
     * @param id
     * @return
     */
    public void deleteById(Integer id) {
        paperMapper.deleteById(id);

    }

    /**
     * 论文更新接口
     * @param paper
     * @return
     */
    public void paperUpdate(Paper paper) {
        paperMapper.paperUpdate(paper);
    }


}
