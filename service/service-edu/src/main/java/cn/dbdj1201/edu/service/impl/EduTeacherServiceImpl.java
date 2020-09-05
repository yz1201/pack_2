package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduTeacher;
import cn.dbdj1201.edu.mapper.EduTeacherMapper;
import cn.dbdj1201.edu.service.IEduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {

    @Override
    public List<EduTeacher> hotTeachers() {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 4");
        return this.list(queryWrapper);
    }
}
