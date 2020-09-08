package cn.dbdj1201.edu.service;

import cn.dbdj1201.common.utils.ordervo.CourseWebVoOrder;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.frontvo.CourseFrontVo;
import cn.dbdj1201.edu.entity.frontvo.CourseWebVo;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     *
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询要发布的课程信息
     *
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishedCourse(String courseId);

    /**
     * 根据课程id删除课程，要删除课程，要删除课程下的所有章节，要删除每个章节下的所有小节，要删除小节下的每个视频，从内层删除
     *
     * @param courseId
     */
    void removeCourseById(String courseId);

    /**
     * 获取排名前八的课程，按id降序之后，前八的。
     * @return
     */
    List<EduCourse> getHotCourses();

    /**
     * 查询前台课程页
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> pageCourseByCondition(Long page, Long limit, CourseFrontVo courseFrontVo);

    /**
     *  根据课程id查询课程信息
     * @param courseId
     * @return
     */
    CourseWebVo getCourseWebVo(String courseId);

    /**
     * 根据课程id获取生成订单所需课程信息
     * @param courseId
     * @return
     */
    CourseWebVoOrder getCourseInfoOrderById(String courseId);
}
