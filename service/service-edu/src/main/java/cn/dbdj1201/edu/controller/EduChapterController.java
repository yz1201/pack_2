package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduChapter;
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

    @ApiOperation("添加课程章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        log.info("add edu chapter-{}", eduChapter);
        this.chapterService.save(eduChapter);
        return R.success();
    }

    @ApiOperation("根据id查询章节信息")
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfoById(@PathVariable String chapterId) {
        log.info("find edu chapter-{}", chapterId);
        return R.success().data("chapter", this.chapterService.getById(chapterId));
    }


    @ApiOperation("修改课程章节")
    @PostMapping("/updateChapterById")
    public R updateChapterById(@RequestBody EduChapter eduChapter) {
        log.info("update edu chapter-{}", eduChapter);
        boolean update = this.chapterService.updateById(eduChapter);
        if (!update) {
            log.error("修改章节失败了(●ˇ∀ˇ●)");
            return R.error().message("修改章节失败了(●ˇ∀ˇ●)");
        }
        return R.success();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapterById(@PathVariable String chapterId) {
        log.info("edu chapter delete - {}", chapterId);
        boolean deleted = this.chapterService.deleteAllChapterAndVideos(chapterId);
        if (!deleted) {
            return R.error().message("该章节下仍有内容，请先删除这部分内容");
        }
        return R.success();
    }
}
