package cn.dbdj1201.edu.controller;


import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/edu/teacher")
@Api("后台讲师管理模块")
@Slf4j
public class EduTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;

    @GetMapping("/findAll")
    @ApiOperation("查询全部讲师")
    public List<EduTeacher> findAllTeachers() {
        log.info("调用讲师查询服务");
        return this.eduTeacherService.list();
    }

    //2 逻辑删除讲师的方法

    @PostMapping("/delete/{id}")
    @ApiOperation("逻辑删除讲师的方法")
    public List<EduTeacher> logicalDelete(@PathVariable("id") String id) {
        this.eduTeacherService.removeById(id);
        return this.eduTeacherService.list();
    }

    //3 分页查询讲师的方法
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询讲师的方法")
    public Page<EduTeacher> pageQuery(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        Page<EduTeacher> page = new Page<>(currentPage, pageSize);
        return this.eduTeacherService.page(page);
    }

    //4 条件查询带分页的方法


    //5 添加讲师接口的方法
    @PostMapping("/add")
    @ApiOperation("添加讲师接口的方法")
    public void add(@RequestBody EduTeacher eduTeacher){

    }

    //6 根据讲师id进行查询

    @GetMapping("/findOne/{id}")
    @ApiOperation("根据讲师id进行查询")
    public EduTeacher findOne(@PathVariable String id) {
        return this.eduTeacherService.getById(id);
    }
    //7 讲师修改功能
    public void modifyTeacher(){

    }

}
