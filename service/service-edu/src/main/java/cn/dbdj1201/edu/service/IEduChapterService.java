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

    /**
     * 查询章节下的全部内容vo
     * @param courseId
     * @return
     */
    List<ChapterVo> findChaptersAndVideosByCourseId(String courseId);

    void addChapterAndVideos(EduChapter eduChapter);

    /**
     * 如果章节下边的小节为空，可以随便删除，如果不为空，禁止直接删除该章节
     * @param chapterId
     * @return 删除失败-false
     */
    boolean deleteAllChapterAndVideos(String chapterId);

    void removeChapterByCourseId(String courseId);
}
