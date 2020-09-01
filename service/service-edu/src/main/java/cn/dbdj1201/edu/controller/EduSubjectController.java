package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.service.IEduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-01
 */
@RestController
@RequestMapping("/edu/subject")
@Api("课程分类模块")
@Slf4j
public class EduSubjectController {

    @Autowired
    private IEduSubjectService subjectService;

    @ApiOperation("添加课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        log.info("调用课程分类存储服务-{}", file);
        this.subjectService.saveSubject(file, subjectService);
        return R.success();
    }
}
