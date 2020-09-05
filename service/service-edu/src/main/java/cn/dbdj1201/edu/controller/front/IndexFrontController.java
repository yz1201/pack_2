package cn.dbdj1201.edu.controller.front;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 18:18
 */
@RestController
@RequestMapping("/edu/front")
@Api("前台课程管理模块")
@CrossOrigin
@Slf4j
public class IndexFrontController {

    @Autowired
    private IEduTeacherService teacherService;

    @Autowired
    private IEduCourseService courseService;

    @ApiOperation("查询热门讲师")
    @GetMapping("/getHotTeachers")
    public R getHotTeachers() {
        //查询前八条热门讲师
        List<EduTeacher> hotTeachers = this.teacherService.hotTeachers();
        return R.success().data("hotTeachers", hotTeachers);
    }

    @ApiOperation("查询热门课程")
    @GetMapping("/getHotCourses")
    public R getHotCourses() {
        //查询前八条热门课程
        List<EduCourse> hotCourses = this.courseService.getHotCourses();
        return R.success().data("hotCourses", hotCourses);
    }
}
