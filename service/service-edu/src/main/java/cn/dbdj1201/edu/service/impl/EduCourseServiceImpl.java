package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.edu.entity.EduChapter;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.entity.EduCourseDescription;
import cn.dbdj1201.edu.entity.EduVideo;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.entity.vo.CoursePublishVo;
import cn.dbdj1201.edu.mapper.EduCourseMapper;
import cn.dbdj1201.edu.service.IEduChapterService;
import cn.dbdj1201.edu.service.IEduCourseDescriptionService;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduVideoService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@Slf4j
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    @Autowired
    private IEduCourseDescriptionService descriptionService;

    @Autowired
    private IEduVideoService videoService;

    @Autowired
    private IEduChapterService chapterService;

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
        EduCourse eduCourse = this.baseMapper.selectById(courseId);
        log.info("根据课程id查询全部课程信息-{}", eduCourse);
        BeanUtil.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription description = this.descriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        log.info("包装后端课程信息vo - {}", courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtil.copyProperties(courseInfoVo, eduCourse);
        log.info("parsed edu course - {}", eduCourse);
        boolean updateCourse = this.updateById(eduCourse);
        if (!updateCourse) {
            log.error("更新课程失败");
            throw new GOLException(20001, "更新课程？没门，不对，好像是你有问题。");
        }


        EduCourseDescription description = new EduCourseDescription();
        BeanUtil.copyProperties(courseInfoVo, description);
        log.info("parsed edu course description- {}", description);
        this.descriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishedCourse(String courseId) {
        return this.baseMapper.getPublishedCourse(courseId);
    }

    @Override
    public void removeCourseById(String courseId) {
        /*
        根据课程id删除课程，要删除课程，要删除课程下的所有章节，要删除每个章节下的所有小节，要删除小节下的每个视频
         */

        //删除小节
        boolean remove = this.videoService.removeVideosByCourseId(courseId);
        if (!remove) {
            throw new GOLException(20001, "删除课程下所有章节的全部小节失败");
        }

        //删除章节
        this.chapterService.removeChapterByCourseId(courseId);

        //删除课程描述
        this.descriptionService.removeById(courseId);

        //删除课程
        boolean deleteFlag = this.removeById(courseId);
        if (!deleteFlag) {
            log.error("删除课程信息失败");
            throw new GOLException(20001, "删除课程信息失败");
        }
    }

    @Override
    public List<EduCourse> getHotCourses() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        return this.list(queryWrapper);
    }
}
