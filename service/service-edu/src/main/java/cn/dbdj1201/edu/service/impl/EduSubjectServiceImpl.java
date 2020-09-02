package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduSubject;
import cn.dbdj1201.edu.entity.excel.ExcelSubjectData;
import cn.dbdj1201.edu.entity.subject.MainSubject;
import cn.dbdj1201.edu.entity.subject.SecondSubject;
import cn.dbdj1201.edu.listener.SubjectListener;
import cn.dbdj1201.edu.mapper.EduSubjectMapper;
import cn.dbdj1201.edu.service.IEduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    @Override
    public void addSubject(MultipartFile file, IEduSubjectService subjectService) {

        try {
            log.info("使用easy-excel 读取excel表格，进行课程分类的存储工作");
            EasyExcel.read(file.getInputStream(), ExcelSubjectData.class, new SubjectListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    /**
     * 查询所有一二级分类VO，二级分类嵌套在一级分类中
     * @return
     */
    @Override
    public List<MainSubject> getAllSubjects() {

        /*
        查询出所有课程分类，二级分类嵌套进一级分类
         */

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);

        List<EduSubject> list = this.list(queryWrapper);
        List<MainSubject> mainSubjects = new ArrayList<>();

        for (EduSubject eduMainSubject : list) {
            String pid = eduMainSubject.getId();
            List<EduSubject> eduSecondSubjects = this.list(new QueryWrapper<EduSubject>().eq("parent_id", pid));

            List<SecondSubject> secondSubjects = eduSecondSubjects.stream().map(subject -> {
                SecondSubject secondSubject = new SecondSubject();
                secondSubject.setId(subject.getId());
                secondSubject.setTitle(subject.getTitle());
                return secondSubject;
            }).collect(Collectors.toList());

            MainSubject mainSubject = new MainSubject();
            mainSubject.setId(eduMainSubject.getId());
            mainSubject.setTitle(eduMainSubject.getTitle());
            mainSubject.setChildren(secondSubjects);
            mainSubjects.add(mainSubject);
        }
        return mainSubjects;
    }
}
