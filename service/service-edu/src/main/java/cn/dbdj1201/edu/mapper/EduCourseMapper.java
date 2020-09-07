package cn.dbdj1201.edu.mapper;

import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.frontvo.CourseWebVo;
import cn.dbdj1201.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishedCourse(String courseId);

    CourseWebVo getCourseWebVo(String courseId);
}
