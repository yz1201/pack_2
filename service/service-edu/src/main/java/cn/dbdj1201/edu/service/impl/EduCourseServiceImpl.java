package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.EduCourseDescription;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.mapper.EduCourseMapper;
import cn.dbdj1201.edu.service.IEduCourseDescriptionService;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    @Autowired
    private IEduCourseDescriptionService descriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        /*
        将前端传过来的vo拆解到对应po中
         */
        // 课程ID 课程讲师ID 课程专业ID  课程标题 课程销售价格 总课时 课程封面图片路径
        EduCourse eduCourse = new EduCourse();
        BeanUtil.copyProperties(courseInfoVo, eduCourse);
        log.info("parsed edu course - {}", eduCourse);
        EduCourseDescription description = new EduCourseDescription();
        BeanUtil.copyProperties(courseInfoVo, description);
        log.info("parsed edu course description- {}", description);

        boolean save = this.save(eduCourse);

        if (!save) {
            throw new GOLException(20001, "添加失败了？😫");
        }

        log.info("入库之后的课程信息？-{}", eduCourse);
        //因为课程跟课程描述是一对一的关系，所以课程描述的id必须与课程一致。
        description.setId(eduCourse.getId());
        this.descriptionService.save(description);

        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = this.getById(courseId);
        log.info("根据课程id查询全部课程信息-{}", eduCourse);
        BeanUtil.copyProperties(eduCourse, courseInfoVo);
        log.info("包装后端课程信息vo - {}", courseInfoVo);
        return courseInfoVo;
    }
}
