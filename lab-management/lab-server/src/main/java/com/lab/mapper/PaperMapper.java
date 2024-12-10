package com.lab.mapper;

import com.github.pagehelper.Page;
import com.lab.dto.PaperAddDTO;
import com.lab.dto.PaperQueryDTO;
import com.lab.entity.Paper;
import com.lab.vo.PaperVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaperMapper {

    Page<PaperVo> pageQuery(PaperQueryDTO paperQueryDTO);

    void paperAdd(PaperAddDTO paperAddDTO);

    @Select("select * from paper where id=#{id}")
    PaperVo getById(Integer id);

    @Delete("delete from paper where id=#{id}")
    void deleteById(Integer id);

    void paperUpdate(Paper paper);
}
