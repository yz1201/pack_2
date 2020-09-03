package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
public interface IEduCourseService extends IService<EduCourse> {

    /**
     * 保存前端来的课程信息
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
