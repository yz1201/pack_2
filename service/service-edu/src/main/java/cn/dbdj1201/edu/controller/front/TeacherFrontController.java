package cn.dbdj1201.edu.controller.front;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 18:44
 */
@RestController
@RequestMapping("/edu/front/teacher")
@Slf4j
@Api("讲师前台接口")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private IEduTeacherService teacherService;

    @Autowired
    private IEduCourseService courseService;

    @ApiOperation("获取讲师列表分页")
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(
            @PathVariable Long page,
            @PathVariable Long limit
    ) {
        log.info("前台分页查询讲师 page-{}，limit-{}", page, limit);
        Map<String, Object> map = this.teacherService.getFrontTeacherList(page, limit);
        return R.success().data(map);
    }

    @ApiOperation("前台讲师详情接口")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {

        /*
        根据讲师id查 讲师基本信息，查讲师下所有课程信息
         */
        log.info("根据讲师id查询-{}", teacherId);

        EduTeacher teacher = this.teacherService.getById(teacherId);
        log.info("讲师查询完毕-{}", teacher);
        List<EduCourse> courses = this.courseService.list(new QueryWrapper<EduCourse>()
                .eq("teacher_id", teacherId));
        log.info("该讲师讲授课程有-{}", courses);
        return R.success().data("teacher", teacher).data("courseList", courses);
    }

}
