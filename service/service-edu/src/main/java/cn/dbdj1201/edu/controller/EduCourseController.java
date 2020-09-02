package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        log.info("调用查询课程信息业务,课程id -{}", courseId);
        CourseInfoVo courseInfoVo = this.courseService.getCourseInfo(courseId);
        return R.success().data("courseInfoVo", courseInfoVo);
    }
}
