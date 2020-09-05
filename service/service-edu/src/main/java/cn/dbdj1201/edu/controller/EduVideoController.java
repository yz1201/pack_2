package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.entity.EduVideo;
import cn.dbdj1201.edu.service.IEduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/edu/video")
@Api("章节下小节模块")
@Slf4j
@CrossOrigin
public class EduVideoController {

    @Autowired
    private IEduVideoService videoService;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        log.info("添加章节的小节-{}", eduVideo);
        this.videoService.save(eduVideo);
        return R.success().message("添加小节成功");
    }

    //删除小节,同时删除视频
    @ApiOperation("删除小节")
    @DeleteMapping("/delete/{videoId}")
    public R deleteVideoById(@PathVariable String videoId) {
        //此处videoId只是小节的id，并不是阿里云的videoId，不要混淆了
        log.info("删除章节的小节-{}", videoId);
        boolean flag = this.videoService.removeVideoById(videoId);
        if (flag) {
            return R.success().message("删除小节成功");
        }
        return R.error().message("删除小节失败");
    }

    //修改小节
    @PostMapping("/updateVideoById")
    public R updateVideoById(@RequestBody EduVideo eduVideo) {
        log.info("修改章节的小节-{}", eduVideo);
        this.videoService.updateById(eduVideo);
        return R.success();
    }

    @ApiOperation("根据id查询小节")
    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        log.info("根据id查询小节-{}", id);
        return R.success().data("video", this.videoService.getById(id));

    }


}
