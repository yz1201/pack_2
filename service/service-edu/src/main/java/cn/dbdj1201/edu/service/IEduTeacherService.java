package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-29
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    /**
     * 获取热门讲师 id升序，前四个
     * @return
     */
    List<EduTeacher> hotTeachers();
}
