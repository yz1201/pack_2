package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.edu.entity.*;
import cn.dbdj1201.edu.entity.frontvo.CourseFrontVo;
import cn.dbdj1201.edu.entity.frontvo.CourseWebVo;
import cn.dbdj1201.edu.entity.vo.CourseInfoVo;
import cn.dbdj1201.edu.entity.vo.CoursePublishVo;
import cn.dbdj1201.edu.mapper.EduCourseMapper;
import cn.dbdj1201.edu.service.IEduChapterService;
import cn.dbdj1201.edu.service.IEduCourseDescriptionService;
import cn.dbdj1201.edu.service.IEduCourseService;
import cn.dbdj1201.edu.service.IEduVideoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Cacheable(cacheNames = "HOT", key = "'SelectList'")
    @Override
    public List<EduCourse> getHotCourses() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        return this.list(queryWrapper);
    }

    //条件查询，每次查询都要变的，做缓存的意义是？
    //@Cacheable(cacheNames = "FRONT", key = "'SelectCoursePage'")
    @Override
    public Map<String, Object> pageCourseByCondition(Long page, Long limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCondition = new Page<>(page, limit);
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        //根据一二级分类id查询
        String subjectParentId = courseFrontVo.getSubjectParentId();
        String subjectId = courseFrontVo.getSubjectId();
        //非空时查该一级分类下的课程，如后台
        if (StrUtil.isNotEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        //非空时查该一级分类下 二级分类包含内容 如后台-java
        if (StrUtil.isNotEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        //排序字段,前台处理时，这部分字段只会传过来一个非空值，所以只会按某一个字段排序。
        String buyCountSort = courseFrontVo.getBuyCountSort();
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        String priceSort = courseFrontVo.getPriceSort();

        if (StrUtil.isNotEmpty(buyCountSort)) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (StrUtil.isNotEmpty(gmtCreateSort)) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (StrUtil.isNotEmpty(priceSort)) {
            queryWrapper.orderByDesc("price");
        }

        Page<EduCourse> eduCoursePage = this.page(pageCondition, queryWrapper);
        log.info("前台课程分页条件查询完毕-{}", eduCoursePage.getRecords());

        List<EduCourse> records = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        long size = eduCoursePage.getSize();
        long current = eduCoursePage.getCurrent();
        long pages = eduCoursePage.getPages();

        boolean hasNext = eduCoursePage.hasNext();
        boolean hasPrevious = eduCoursePage.hasPrevious();

        resultMap.put("records", records);
        resultMap.put("total", total);
        resultMap.put("size", size);
        resultMap.put("current", current);
        resultMap.put("pages", pages);
        resultMap.put("hasNext", hasNext);
        resultMap.put("hasPrevious", hasPrevious);
        return resultMap;
    }

    /**
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getCourseWebVo(String courseId) {
         /*
        根据课程id查询课程信息 查看CourseWebVo，对应着写sql语句，多表关联
            -课程基本信息
            -课程详情 1-1
            -课程分类 1-n
            -课程讲师 1-n
         */
        CourseWebVo courseWebVo = this.baseMapper.getCourseWebVo(courseId);
        log.info("课程webVo查询完毕-{}", courseWebVo);
        return courseWebVo;
    }
}
