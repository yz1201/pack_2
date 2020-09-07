package cn.dbdj1201.edu.controller.front;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.chapter.ChapterVo;
import cn.dbdj1201.edu.entity.frontvo.CourseFrontVo;
import cn.dbdj1201.edu.entity.frontvo.CourseWebVo;
import cn.dbdj1201.edu.service.IEduChapterService;
import cn.dbdj1201.edu.service.IEduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-07 8:45
 */
@RestController
@RequestMapping("/edu/front/course")
@Slf4j
@Api("讲师前台接口")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private IEduCourseService courseService;

    @Autowired
    private IEduChapterService chapterService;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @PathVariable(required = false) Long page, @PathVariable(required = false) Long limit,
            @RequestBody(required = false) CourseFrontVo courseFrontVo
    ) {

        log.info("前台课程分页条件查询，page-{}, limit-{}", page, limit);
        log.info("前台课程分页条件查询，condition-{}", courseFrontVo);
        if (page == null) {
            page = 1L;
        }

        if (limit == null) {
            limit = 8L;
        }

        Map<String, Object> map = this.courseService.pageCourseByCondition(page, limit, courseFrontVo);
        return R.success().data(map);
    }

    @ApiOperation("课程详情")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        /*
        根据课程id查询课程信息
            -课程基本信息
            -课程详情 1-1
            -课程分类 1-n
            -课程讲师 1-n
         */
        log.info("课程详情查询id-{}", courseId);
        CourseWebVo courseWebVo = this.courseService.getCourseWebVo(courseId);
        /*
        根据课程id获取章节小节，n-1
         */
        List<ChapterVo> chapterVos = this.chapterService.findChaptersAndVideosByCourseId(courseId);
        log.info("课程下内容查询完毕-{}", chapterVos);
        return R.success().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVos);
    }
}
