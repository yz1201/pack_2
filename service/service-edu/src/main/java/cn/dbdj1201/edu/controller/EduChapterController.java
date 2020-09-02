package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.chapter.ChapterVo;
import cn.dbdj1201.edu.service.IEduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/edu/chapter")
@Slf4j
@Api("课程章节模块")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private IEduChapterService chapterService;

    @ApiOperation("查找所有章节")
    @GetMapping("/findAllChapterVideos/{courseId}")
    public R findAllChapterVideos(@PathVariable("courseId") String courseId) {
        log.info("调用查询所有章节及下子节点业务,课程id-{}", courseId);
        List<ChapterVo> chapterVos = this.chapterService.findChaptersAndVideosByCourseId(courseId);
        return R.success().data("chapterVos", chapterVos);
    }

    @GetMapping("/findAll")
    public R findAll() {
        return R.success().data("list", this.chapterService.list());
    }


}
