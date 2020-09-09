package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.entity.vo.TeacherQuery;
import cn.dbdj1201.edu.service.IEduTeacherService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;

    @GetMapping("/findAll")
    @ApiOperation("查询全部讲师")
    public R findAllTeachers() {
        log.info("调用讲师查询服务");
        List<EduTeacher> list = this.eduTeacherService.list();
        return R.success().data("items", list);
    }

    //2 逻辑删除讲师的方法
    @PostMapping("/delete/{id}")
    @ApiOperation("逻辑删除讲师的方法")
    public R logicalDelete(@PathVariable("id") String id) {
        boolean removeById = this.eduTeacherService.removeById(id);
        if (removeById) {
            return R.success();
        } else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询讲师的方法")
    public R pageQuery(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        Page<EduTeacher> page = new Page<>(currentPage, pageSize);
        IPage<EduTeacher> eduTeacherPage = this.eduTeacherService.page(page);
        log.info("data-{}", eduTeacherPage.getTotal());
        return R.success().data("total", eduTeacherPage.getTotal()).data("records", eduTeacherPage);
    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "分页查询带条件")
    @PostMapping("pageTeacherByCondition/{currentPage}/{pageSize}")
    public R pageTeacherCondition(@PathVariable long currentPage, @PathVariable long pageSize,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        log.info("input info - {} {} {}", currentPage, pageSize, teacherQuery);

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        /*
        构造查询sql
         */
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }

        if (level != null && level >= 0) {
            queryWrapper.eq("level", level);
        }

        if (StrUtil.isNotBlank(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (StrUtil.isNotBlank(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");

        Page<EduTeacher> teacherPage = this.eduTeacherService.page(new Page<>(currentPage, pageSize), queryWrapper);
        log.info("data-{}", teacherPage.getRecords());
        return R.success().data("total", teacherPage.getTotal()).data("records", teacherPage);
    }

    //5 添加讲师接口的方法
    @PostMapping("/add")
    @ApiOperation("添加讲师接口的方法")
    public R add(@RequestBody EduTeacher eduTeacher) {
        log.info("接受到的讲师信息为{}", eduTeacher);
        boolean save = this.eduTeacherService.save(eduTeacher);
        if (save) {
            return R.success();
        } else {
            return R.error();
        }
    }

    //6 根据讲师id进行查询
    @GetMapping("/findOne/{id}")
    @ApiOperation("根据讲师id进行查询")
    public R findOne(@PathVariable String id) {
        return R.success().data("item", this.eduTeacherService.getById(id));
    }

    //7 讲师修改功能
    @PostMapping("/modifyInfo")
    @ApiOperation("讲师信息修改")
    public R modifyTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean updateById = this.eduTeacherService.updateById(eduTeacher);
        if (updateById) {
            return R.success();
        }
        return R.error();
    }

}
