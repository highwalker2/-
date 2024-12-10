package com.lab.controller;

import com.lab.dto.PaperAddDTO;
import com.lab.dto.PaperQueryDTO;
import com.lab.entity.Paper;
import com.lab.result.PageResult;
import com.lab.result.Result;
import com.lab.service.PaperService;
import com.lab.utils.AliOssUtil;
import com.lab.vo.PaperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/paper")
@Api(tags = "论文相关接口")
@Slf4j
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:7000"}) // 允许来自 http://localhost:7000 的请求
public class PaperController {

    @Autowired
    private PaperService paperService;
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 论文分页查询接口
     * @param paperQueryDTO
     * @return
     */
    @GetMapping("/paperQuery")
    @ApiOperation("论文分页查询接口")
    public Result<PageResult> paperList(PaperQueryDTO paperQueryDTO) {
        log.info("论文分页查询：{}", paperQueryDTO);
        PageResult pageResult=paperService.pageQuery(paperQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 论文添加接口
     * @param paperAddDTO
     * @return
     */
    @PostMapping("/addPaper")
    @ApiOperation("论文添加接口")
    public Result addPaper(@RequestBody PaperAddDTO paperAddDTO) {
        paperService.paperAdd(paperAddDTO);
        return Result.success();
    }

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传接口")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            String filePath=aliOssUtil.upload(file.getBytes(),originalFilename);

            return Result.success(filePath);
        }catch (Exception e){
            log.info("文件上传失败：{}",e);
        }

        return Result.error("文件上传失败");
    }

    /**
     * 论文详情获取接口
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("论文详情获取接口")
    public Result<PaperVo> paperDetail(@PathVariable("id") Integer id) {
        log.info("当前需要获取论文的id:{}",id);
        PaperVo paperVo=paperService.getById(id);

        return Result.success(paperVo);
    };

    /**
     * 删除论文的接口
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation("删除论文的接口")
    public Result papperDelete(@PathVariable("id") Integer id) {
        log.info("删除论文的id:{}",id);
        paperService.deleteById(id);
        return  Result.success();
    }

    /**
     * 论文更新接口
     * @param paper
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新论文接口")
    public Result update(@RequestBody Paper paper) {
        paperService.paperUpdate(paper);
        return Result.success();
    }
}
