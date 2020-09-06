package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.mapper.EduTeacherMapper;
import cn.dbdj1201.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-29
 */
@Service
@Slf4j
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {

    @Cacheable(cacheNames = "HOT", key = "'SelectTeacherList'")
    @Override
    public List<EduTeacher> hotTeachers() {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 4");
        return this.list(queryWrapper);
    }

    @Override
    public Map<String, Object> getFrontTeacherList(Long page, Long limit) {
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<EduTeacher> teacherPage = this.page(new Page<>(page, limit), queryWrapper);
        log.info("前台讲师查询完成-{}", teacherPage.getRecords());

        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long current = teacherPage.getCurrent();
        long pages = teacherPage.getPages();

        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

        resultMap.put("records", records);
        resultMap.put("total", total);
        resultMap.put("size", size);
        resultMap.put("current", current);
        resultMap.put("pages", pages);
        resultMap.put("hasNext", hasNext);
        resultMap.put("hasPrevious", hasPrevious);

        return resultMap;
    }
}
