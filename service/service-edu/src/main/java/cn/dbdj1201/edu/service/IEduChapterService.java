package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduChapter;
import cn.dbdj1201.edu.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
public interface IEduChapterService extends IService<EduChapter> {

    List<ChapterVo> findChaptersAndVideosByCourseId(String courseId);
}
