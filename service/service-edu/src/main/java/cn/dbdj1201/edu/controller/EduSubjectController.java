package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduSubject;
import cn.dbdj1201.edu.entity.subject.MainSubject;
import cn.dbdj1201.edu.service.IEduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/edu/subject")
@Api("课程分类模块")
@Slf4j
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private IEduSubjectService subjectService;

    @ApiOperation("添加课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        log.info("调用课程分类存储服务-{}", file);
        this.subjectService.addSubject(file, subjectService);
        return R.success();
    }


    @GetMapping("/findAll")
    @ApiOperation("查询全部课程分类")
    public R findAllSubjects() {
        List<MainSubject> allSubjects = this.subjectService.getAllSubjects();
        log.info("调用课程分类查询服务，查询结果为-{}", allSubjects);
        return R.success().data("subjects", allSubjects);
    }

//    @GetMapping("/findAllMainSubject")
//    @ApiOperation("查询所有一级分类")
//    public R findAllMainSubject() {
//        List<EduSubject> mainSubjects = this.subjectService
//                .list(new QueryWrapper<EduSubject>().eq("parent_id", 0));
//        log.info("调用课程一级分类查询服务，结果为-{}", mainSubjects);
//        return R.success().data("list", mainSubjects);
//    }


}
