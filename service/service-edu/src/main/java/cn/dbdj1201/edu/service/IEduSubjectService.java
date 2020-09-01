package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-01
 */
public interface IEduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     */
    void saveSubject(MultipartFile file, IEduSubjectService subjectService);
}
