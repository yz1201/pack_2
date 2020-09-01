package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduSubject;
import cn.dbdj1201.edu.entity.excel.ExcelSubjectData;
import cn.dbdj1201.edu.listener.SubjectExcelListener;
import cn.dbdj1201.edu.mapper.EduSubjectMapper;
import cn.dbdj1201.edu.service.IEduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-01
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, IEduSubjectService subjectService) {

        try {
            log.info("使用easy-excel 读取excel表格，进行课程分类的存储工作");
            EasyExcel.read(file.getInputStream(), ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }


    }
}
