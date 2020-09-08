package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.ordervo.CourseWebVoOrder;
import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.entity.vo.CoursePublishVo;
import cn.dbdj1201.edu.entity.vo.CourseQuery;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/edu/course")
@Api("课程管理模块")
@CrossOrigin
@Slf4j
public class EduCourseController {

    @Autowired
    private IEduCourseService courseService;

    @Autowired
    private IEduTeacherService teacherService;

    @ApiOperation("添加课程信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        log.info("调用添加课程信息服务-{}", courseInfo);
        String id = this.courseService.saveCourseInfo(courseInfo);
        return R.success().data("courseId", id);
    }

    @ApiOperation("查询所有讲师")
    @GetMapping("/findAllTeachers")
    public R findAllTeachers() {
        return R.success().data("items", this.teacherService.list());
    }

    @ApiOperation("根据课程id获取相应课程信息")
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable("courseId") String courseId) {
        log.info("调用查询课程信息业务,课程id -{}-", courseId);
        CourseInfoVo courseInfoVo = this.courseService.getCourseInfo(courseId);
        return R.success().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("根据课程id获取相应课程全部信息")
    @GetMapping("/getCourseById/{courseId}")
    public R getCourseById(@PathVariable("courseId") String courseId) {
        log.info("调用查询课程信息业务,课程id -{}-", courseId);
        EduCourse eduCourse = this.courseService.getById(courseId);
        log.info("调用查询课程信息业务,result -{}-", eduCourse);
        return R.success().data("eduCourse", eduCourse);
    }

    @PostMapping("/updateCourseInfo")
    @ApiOperation("修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        log.info("update courseInfo-{}", courseInfoVo);
        this.courseService.updateCourseInfo(courseInfoVo);
        return R.success();
    }

    @GetMapping("/getPublishedCourse/{courseId}")
    @ApiOperation("根据id查询发布课程信息")
    public R getPublishedCourse(@PathVariable String courseId) {
        log.info("根据课程id查询要发布的课程信息-{}", courseId);
        CoursePublishVo coursePublishVo = this.courseService.getPublishedCourse(courseId);
        log.info("发布课程信息-{}", coursePublishVo);
        return R.success().data("coursePublishVo", coursePublishVo);
    }

    @ApiOperation("发布课程")
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        /*
        还是不熟悉mp，只需将要修改的字段传入即可，其他不用管
         */
//        EduCourse eduCourse = this.courseService.getById(courseId);
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        this.courseService.updateById(eduCourse);

        return R.success();
    }


    @ApiOperation("分页条件查询课程信息")
    @PostMapping("/getCourseListPageByCondition/{page}/{limit}")
    public R getCourseListPageByCondition(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false, value = "limit") Integer limit,
            @RequestBody(required = false) CourseQuery courseQuery
    ) {

        log.info("condition query limit-{}, page-{}, condition-{}", limit, page, courseQuery);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if (page < 1) {
            page = 1;
        }

        if (limit < 0) {
            limit = 3;
        }

        if (courseQuery != null) {

            if (courseQuery.getTitle() != null) {
                queryWrapper.like("title", courseQuery.getTitle());
            }

            if (courseQuery.getStatus() != null) {
                queryWrapper.eq("status", courseQuery.getStatus());
            }

        }

        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Page<EduCourse> coursePage = this.courseService.page(eduCoursePage, queryWrapper);
        log.info("条件查询的结果为{}, 总条数-{}", coursePage.getRecords(), coursePage.getTotal());
        return R.success().data("records", coursePage.getRecords()).data("total", coursePage.getTotal());
    }

    @ApiOperation("根据id删除课程")
    @PostMapping("/delete/{courseId}")
    public R deleteCourseById(@PathVariable String courseId) {
        /*
         小节的视频别忘了删除，暂时还没有 要删除的有所有章节，章节下的所有小节，小节下还有视频
         */
        log.info("删除课程-{}", courseId);
        this.courseService.removeCourseById(courseId);
        return R.success();
    }

    @ApiOperation("获取某天课程新增数")
    @GetMapping("/getAddCourseCount/{date}")
    public R getAddCourseCount(@PathVariable("date") String date) {
        log.info("查询{}的新增课程数", date);
        return R.success().data("courseCount", this.courseService.getAddCourseCount(date));
    }

}
