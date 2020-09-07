package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduSubject;
import cn.dbdj1201.edu.entity.subject.MainSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
public interface IEduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     *
     * @param file           上传的excel表格
     * @param subjectService
     */
    void addSubject(MultipartFile file, IEduSubjectService subjectService);

    /**
     * 查询所有一二级分类VO，二级分类嵌套在一级分类中
     *
     * @return
     */
    List<MainSubject> getAllSubjects();
}
