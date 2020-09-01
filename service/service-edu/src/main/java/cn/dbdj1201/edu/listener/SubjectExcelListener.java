package cn.dbdj1201.edu.listener;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.edu.entity.EduSubject;
import cn.dbdj1201.edu.entity.excel.ExcelSubjectData;
import cn.dbdj1201.edu.service.IEduSubjectService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yz1201
 * @date 2020-09-01 21:33
 **/
@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private IEduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(IEduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 按行读取excel中的内容，读到内存
     *
     * @param excelSubjectData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData == null) {
            throw new GOLException(20001, "文件数据是空的(⊙o⊙)？");
        }

        /*
            按行读，每次读取有两个值，第一个值一级分类，第二个值二级分类
            已存在的分类不需要重复添加，所以需要先判断分类是否存在
         */

        //存储一级分类到数据库
        EduSubject mainSubject = this.isExistsMainSubject(excelSubjectData.getOneSubjectName());
        if (mainSubject == null) {
            mainSubject = new EduSubject();
            mainSubject.setParentId("0");
            mainSubject.setTitle(excelSubjectData.getOneSubjectName());
            this.eduSubjectService.save(mainSubject);
        }

        //存储二级分类到数据库表
        String pid = mainSubject.getId();
        EduSubject secondSubject = this.isExistsSecondSubject(excelSubjectData.getTwoSubjectName(), pid);
        if (secondSubject == null) {
            secondSubject = new EduSubject();
            secondSubject.setTitle(excelSubjectData.getTwoSubjectName());
            secondSubject.setParentId(pid);
            this.eduSubjectService.save(secondSubject);
        }

    }

    /**
     * 根据title跟父分类id为0获取一级分类
     *
     * @param name
     * @return true 存在， false 不存在
     */
    public EduSubject isExistsMainSubject(String name) {
        return this.eduSubjectService
                .getOne(new QueryWrapper<EduSubject>()
                        .eq("title", name)
                        .eq("parent_id", 0));
    }

    /**
     * 根据名称跟父分类id查询二级分类
     *
     * @param name
     * @param pid  父分类id
     * @return
     */
    public EduSubject isExistsSecondSubject(String name, String pid) {
        return this.eduSubjectService
                .getOne(new QueryWrapper<EduSubject>()
                        .eq("title", name)
                        .eq("parent_id", pid));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
