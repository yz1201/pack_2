package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduChapter;
import cn.dbdj1201.edu.entity.EduVideo;
import cn.dbdj1201.edu.entity.chapter.ChapterVo;
import cn.dbdj1201.edu.entity.chapter.VideoVo;
import cn.dbdj1201.edu.mapper.EduChapterMapper;
import cn.dbdj1201.edu.service.IEduChapterService;
import cn.dbdj1201.edu.service.IEduVideoService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@Service
@Slf4j
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {


    @Autowired
    private IEduVideoService videoService;

    /**
     * 根据课程id查询下属所有章节及每章的子节点(比如第一章分几小讲)
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> findChaptersAndVideosByCourseId(String courseId) {
        List<EduChapter> eduChapters = this.list(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        log.info("根据课程id查询章节节点-{}", eduChapters);
        //先给章节搞出来 再根据章节id去凑下边的节点
        List<ChapterVo> chapterVos = new ArrayList<>();
        eduChapters.forEach(eduChapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtil.copyProperties(eduChapter, chapterVo);
            //查询某章节对应的子节点，填充。
            List<EduVideo> videosChapter = this.videoService.list(new QueryWrapper<EduVideo>().eq("chapter_id", eduChapter.getId()));
            log.info("根据章节id查询子节点-{}", videosChapter);
            List<VideoVo> videoVos = videosChapter.stream().map(vc -> {
                VideoVo videoVo = new VideoVo();
                BeanUtil.copyProperties(vc, videoVo);
                return videoVo;
            }).collect(Collectors.toList());
            chapterVo.setChildren(videoVos);

            chapterVos.add(chapterVo);
        });

        return chapterVos;
    }
}
